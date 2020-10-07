package vn.actvn.onthionline.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.actvn.onthionline.domain.Exam;
import vn.actvn.onthionline.service.dto.ExamDto;
import vn.actvn.onthionline.service.dto.ExamForUserDto;
import vn.actvn.onthionline.service.dto.ExamQuestionDto;
import vn.actvn.onthionline.service.dto.ExamQuestionForUserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamForUserMapper {
    @Autowired
    private ExamQuestionForUserMapper examQuestionMapper;

    public ExamForUserDto toDto(Exam exam) {
        if (exam == null)
            return null;

        ExamForUserDto examDto = new ExamForUserDto();
        examDto.setId(exam.getId());
        examDto.setImage(exam.getImage());
        examDto.setName(exam.getName());
        examDto.setTime(exam.getTime());
        examDto.setNumPeopleDid(exam.getNumPeopleDid());
        examDto.setDescription(exam.getDescription());
        List<ExamQuestionForUserDto> examQuestionDtos = exam.getExamQuestions().stream().map(examQuestionMapper::toDto).collect(Collectors.toList());
        examDto.setExamQuestions(examQuestionDtos);
        return examDto;
    }
}
