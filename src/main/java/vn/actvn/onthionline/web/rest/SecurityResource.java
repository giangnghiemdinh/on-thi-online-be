package vn.actvn.onthionline.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.actvn.onthionline.client.dto.*;
import vn.actvn.onthionline.common.*;
import vn.actvn.onthionline.common.exception.ServiceException;
import vn.actvn.onthionline.common.service.JwtUserDetailsService;
import vn.actvn.onthionline.common.utils.ResponseUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class SecurityResource {
    private Logger LOGGER = LoggerFactory.getLogger(SecurityResource.class);

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping(value = "/auth/token")
    public ResponseEntity<BaseDataResponse<LoginResponse>> createAuthenticationToken(@RequestBody BaseDataRequest<LoginRequest> request, HttpServletRequest httpRequest) throws ServiceException {
        try {
            LoginResponse response = userDetailsService.generateToken(request.getBody(), httpRequest);
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<BaseDataResponse<RegisterResponse>> registerUser(@RequestBody BaseDataRequest<RegisterRequest> request) throws ServiceException{
        try {
            RegisterResponse response = userDetailsService.register(request.getBody());
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<BaseDataResponse<GetOTPResponse>> forgotPassword(@RequestBody BaseDataRequest<GetOTPRequest> request) {
        try {
            GetOTPResponse response = userDetailsService.generateOtp(request.getBody(), Constant.OTP_CHANGE_PASSWORD);
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PostMapping("/generate-otp")
    public ResponseEntity<BaseDataResponse<GetOTPResponse>> generateOtp(@RequestBody BaseDataRequest<GetOTPRequest> request) {
        try {
            GetOTPResponse response = userDetailsService.generateOtp(request.getBody(), Constant.OTP_LOGIN);
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<BaseDataResponse<ChangeForgotPassResponse>> changeForgotPassword(@RequestBody BaseDataRequest<ChangeForgotPassRequest> request) {
        try {
            ChangeForgotPassResponse response = userDetailsService.changeForgotPassword(request.getBody());
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }
    }

}
