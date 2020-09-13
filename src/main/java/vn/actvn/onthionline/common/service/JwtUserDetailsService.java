package vn.actvn.onthionline.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.actvn.onthionline.client.dto.*;
import vn.actvn.onthionline.common.Constant;
import vn.actvn.onthionline.common.ValidationError;
import vn.actvn.onthionline.common.exception.ServiceException;
import vn.actvn.onthionline.common.exception.ServiceExceptionBuilder;
import vn.actvn.onthionline.common.exception.ValidationErrorResponse;
import vn.actvn.onthionline.common.utils.ServiceUtil;
import vn.actvn.onthionline.domain.Role;
import vn.actvn.onthionline.domain.User;
import vn.actvn.onthionline.repository.RoleRepository;
import vn.actvn.onthionline.repository.UserRepository;
import vn.actvn.onthionline.service.dto.EmailDto;
import vn.actvn.onthionline.service.mapper.UserMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokenService jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_USER"));
    }


    public LoginResponse generateToken(LoginRequest request, HttpServletRequest httpRequest) throws ServiceException {
        try {
            if (null == request) ServiceUtil.generateEmptyPayloadError();
            if (null == request.getUsername())
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Username", ValidationError.NotNull))
                        .build();
            if (null == request.getPassword() && null == request.getOtp())
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Password or Otp", ValidationError.NotNull))
                        .build();

            User user = userRepository.findByUsername(request.getUsername());
            if (null == user)
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Username or password/ otp", ValidationError.Invalid))
                        .build();

            if (null != request.getPassword()) {
                if (!bcryptEncoder.matches(request.getPassword(), user.getPassword()))
                    throw ServiceExceptionBuilder.newBuilder()
                            .addError(new ValidationErrorResponse("Username or password/ otp", ValidationError.Invalid))
                            .build();

            }
            else {
                if (!otpService.validateOTP(request.getUsername() + Constant.LOGIN, request.getOtp())) {
                    throw ServiceExceptionBuilder.newBuilder()
                            .addError(new ValidationErrorResponse("Username or password/ otp", ValidationError.Invalid))
                            .build();
                }
            }

            //Update last login
            user.setLastLogin(new Date());
            userRepository.save(user);

            LoginResponse response = new LoginResponse();
            response.setToken(jwtTokenUtil.generate(user, httpRequest));

            return response;
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    //save new user
    public RegisterResponse register(RegisterRequest request) throws ServiceException {
        try {
            if (null == request) ServiceUtil.generateEmptyPayloadError();
            if (null == request.getUserRegister())
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("User Register", ValidationError.NotNull))
                        .build();


            Optional<User> user = Optional.ofNullable(userRepository.findByUsername(request.getUserRegister().getUsername()));
            if (user.isPresent())
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Username", ValidationError.Exists))
                        .build();

            Integer emailExist = userRepository.countByEmail(request.getUserRegister().getEmail());
            if (emailExist > 0)
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Email", ValidationError.Exists))
                        .build();

            User newUser = userMapper.toEntity(request.getUserRegister());
            newUser.setPassword(bcryptEncoder.encode(request.getUserRegister().getPassword()));
            newUser.setCreatedDate(new Date());
            newUser.setIsActive(true);
            newUser.setOnlineTime(0);
            List<Role> roleList = new ArrayList<>();
            roleList.add(roleRepository.findByRoleName(Constant.ROLE_USER));
            newUser.setRoles(roleList);

            RegisterResponse response = new RegisterResponse();
            User saveUser = userRepository.save(newUser);

            if (null != saveUser) {
                response.setSuccess(true);
            } else response.setSuccess(false);

            return response;
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public GetOTPResponse generateOtp(GetOTPRequest request, String purpose) throws ServiceException{
        try {
            if (null == request) ServiceUtil.generateEmptyPayloadError();
            if (null == request.getUsername() && null == request.getEmail())
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Username or email", ValidationError.NotNull))
                        .build();
            if (null != request.getUsername() && null != request.getEmail()) {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("You must be use", "username or email"))
                        .build();
            }

            Optional<User> user = null;
            if (null != request.getUsername()) {
                user = Optional.ofNullable(userRepository.findByUsername(request.getUsername()));
                if (!user.isPresent())
                    throw ServiceExceptionBuilder.newBuilder()
                            .addError(new ValidationErrorResponse("Username", ValidationError.Invalid))
                            .build();
            }

            if (null != request.getEmail()) {
                user = Optional.ofNullable(userRepository.findByEmail(request.getEmail()));
                if (!user.isPresent())
                    throw ServiceExceptionBuilder.newBuilder()
                            .addError(new ValidationErrorResponse("Email", ValidationError.Invalid))
                            .build();
            }

            Integer otpValue = otpService.generateOtp(user.get().getUsername() + (purpose.equals(Constant.OTP_LOGIN) ? Constant.LOGIN : Constant.FORGOT_PASSWORD));

            // Sent mail
            if ( -1 != otpValue) {
                // fetch user e-mail from database
                List<String> recipients = new ArrayList<>();
                recipients.add(user.get().getEmail());

                // generate emailDTO object
                EmailDto emailDTO = new EmailDto();
                emailDTO.setSubject("Ôn thi online - Thi trắc nghiệm trực tuyến miễn phí 2020");
                emailDTO.setBody("Chào bạn " + user.get().getFullname() + "! \n"+ "ĐỂ " + purpose + ", mã xác minh là "+ otpValue +
                        ". Có hiệu lực trong 5 phút. KHÔNG chia sẻ mã này với người khác." +
                        "\n\n\n\nDeal, \nÔn thi online team \nMade by KMA"
                );
                emailDTO.setRecipients(recipients);

                // send generated e-mail
                emailService.sendSimpleMessage(emailDTO);
                return new GetOTPResponse(true);
            }
            else
                return new GetOTPResponse(false);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public ChangeForgotPassResponse changeForgotPassword(ChangeForgotPassRequest request) throws ServiceException {
        try {
            if (null == request) ServiceUtil.generateEmptyPayloadError();
            if (null == request.getOtp())
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Otp", ValidationError.NotNull))
                        .build();

            if (true == otpService.validateOTP(request.getUsername() + Constant.FORGOT_PASSWORD, request.getOtp())) {
                User user = userRepository.findByUsername(request.getUsername());
                user.setPassword(bcryptEncoder.encode(request.getNewPassword()));
                userRepository.save(user);
                return new ChangeForgotPassResponse(true);
            } else {
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("OTP", ValidationError.Invalid))
                        .build();
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

}