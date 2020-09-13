package vn.actvn.onthionline.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.actvn.onthionline.client.dto.*;
import vn.actvn.onthionline.common.exception.ServiceException;
import vn.actvn.onthionline.common.utils.ResponseUtil;
import vn.actvn.onthionline.service.ExamService;

import java.security.Principal;

@RestController
@RequestMapping("/api/exam")
public class ExamResource {
    private Logger LOGGER = LoggerFactory.getLogger(ExamResource.class);

    @Autowired
    private ExamService examService;

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<BaseDataResponse<AddExamResponse>> addExam(@RequestBody BaseDataRequest<AddExamRequest> request, Principal currentUser) {
        try {
            AddExamResponse response = examService.add(request.getBody(), currentUser.getName());
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception e) {
            LOGGER.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/get-exam")
    public ResponseEntity<BaseDataResponse<GetExamResponse>> getExam(@RequestBody BaseDataRequest<GetExamRequest> request) {
        try {
            GetExamResponse response = examService.getExam(request.getBody());
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
