package vn.actvn.onthionline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.actvn.onthionline.client.dto.*;
import vn.actvn.onthionline.common.ValidationError;
import vn.actvn.onthionline.common.exception.ServiceException;
import vn.actvn.onthionline.common.exception.ServiceExceptionBuilder;
import vn.actvn.onthionline.common.exception.ValidationErrorResponse;
import vn.actvn.onthionline.common.utils.ServiceUtil;
import vn.actvn.onthionline.domain.Exam;
import vn.actvn.onthionline.domain.ExamQuestion;
import vn.actvn.onthionline.domain.User;
import vn.actvn.onthionline.repository.ExamQuestionRepository;
import vn.actvn.onthionline.repository.ExamRepository;
import vn.actvn.onthionline.repository.UserRepository;
import vn.actvn.onthionline.service.dto.ExamInfoDto;
import vn.actvn.onthionline.service.mapper.ExamInfoMapper;
import vn.actvn.onthionline.service.mapper.ExamMapper;
import vn.actvn.onthionline.service.mapper.ExamQuestionMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamQuestionRepository examQuestionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ExamQuestionMapper examQuestionMapper;

    @Transactional(rollbackFor = Throwable.class)
    public AddExamResponse add(AddExamRequest request, String username) throws ServiceException {
        try {
            if (null == request) ServiceUtil.generateEmptyPayloadError();
            if (null == request.getExam())
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Exam", ValidationError.NotNull))
                        .build();
            Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
            if (!user.isPresent())
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Something was", ValidationError.Wrong))
                        .build();

            // Save exam
            Exam exam = examMapper.toEntity(request.getExam());
            exam.setNumQuestion(request.getExam().getExamQuestions().size());
            exam.setNumPeopleDid(0);
            exam.setActive(true);
            exam.setUserCreated(username);
            exam.setCreatedDate(new Date());
            Exam examSaved = examRepository.save(exam);

            // Save question
            List<ExamQuestion> examQuestions = request.getExam().getExamQuestions()
                    .stream().map(examQuestionMapper::toEntity).collect(Collectors.toList());
            examQuestions.stream().forEach(question -> {
                question.setExam(examSaved);
                question.setCreatedDate(new Date());
            });
            List<ExamQuestion> examQuestionsSaved = examQuestionRepository.saveAll(examQuestions);

            AddExamResponse response = new AddExamResponse();
            response.setExam(examMapper.toDtoWithQuestion(examSaved, examQuestionsSaved));

            return response;
        } catch (ServiceException e) {
            throw e;
        }
    }

    public GetExamBySubjectResponse getExamBySubject(GetExamBySubjectRequest request) throws ServiceException {
        try {
            if (null == request) ServiceUtil.generateEmptyPayloadError();
            if (null == request.getSubject())
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Subject", ValidationError.NotNull))
                        .build();

            Optional<List<Exam>> exams = examRepository.findAllBySubject(request.getSubject());
            GetExamBySubjectResponse response = new GetExamBySubjectResponse();
            List<ExamInfoDto> examInfoDtos = new ArrayList<>();

            if (!exams.isPresent()) {
                response.setExam(examInfoDtos);
                return response;
            }

            examInfoDtos = exams.get().stream().map(ExamInfoMapper::toDto).collect(Collectors.toList());
            response.setExam(examInfoDtos);
            return response;
        } catch (ServiceException e) {
            throw e;
        }
    }

    public GetExamResponse getExam(GetExamRequest request) throws ServiceException {
        try {
            if (null == request) ServiceUtil.generateEmptyPayloadError();
            if (null == request.getId())
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Id", ValidationError.NotNull))
                        .build();

            Optional<Exam> exam = examRepository.findById(request.getId());

            if (!exam.isPresent())
                throw ServiceExceptionBuilder.newBuilder()
                        .addError(new ValidationErrorResponse("Id", ValidationError.Invalid))
                        .build();

            GetExamResponse response = new GetExamResponse();
            response.setExam(examMapper.toDto(exam.get()));
            return response;
        } catch (ServiceException e) {
            throw e;
        }
    }
}
