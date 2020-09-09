package vn.actvn.onthionline.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.actvn.onthionline.client.dto.*;
import vn.actvn.onthionline.client.dto.BaseDataRequest;
import vn.actvn.onthionline.client.dto.BaseDataResponse;

import vn.actvn.onthionline.common.utils.ResponseUtil;
import vn.actvn.onthionline.common.exception.ServiceException;
import vn.actvn.onthionline.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/profile")
public class UsersResource {
    private Logger LOGGER = LoggerFactory.getLogger(UsersResource.class);

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/upload-img")
    public ResponseEntity<BaseDataResponse<UploadImgProfileResponse>> uploadImgProfile(@RequestBody BaseDataRequest<UploadImgProfileRequest> request, Principal currentUser) {
        try {
            UploadImgProfileResponse response = userService.uploadImgProfile(request.getBody(), currentUser.getName());
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/get-img")
    public ResponseEntity<BaseDataResponse<GetImgProfileResponse>> getImgProfile(Principal currentUser) {
        try {
            GetImgProfileResponse response = userService.getImgProfile(currentUser.getName());
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/get")
    public ResponseEntity<BaseDataResponse<GetProfileInfoResponse>> getProfileInfo(Principal currentUser) {
        try {
            GetProfileInfoResponse response = userService.getProfileInfo(currentUser.getName());
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<BaseDataResponse<UpdateProfileInfoResponse>> updateProfileInfo(@RequestBody BaseDataRequest<UpdateProfileInfoRequest> request, Principal currentUser) {
        try {
            UpdateProfileInfoResponse response = userService.updateProfile(request.getBody(), currentUser.getName());
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/change-password")
    public ResponseEntity<BaseDataResponse<ChangePasswordResponse>> changePassword(@RequestBody BaseDataRequest<ChangePasswordRequest> request, Principal currentUser) {
        try {
            ChangePasswordResponse response = userService.changePassword(request.getBody(), currentUser.getName());
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
