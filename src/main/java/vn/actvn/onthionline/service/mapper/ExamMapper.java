package vn.actvn.onthionline.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.actvn.onthionline.domain.Exam;
import vn.actvn.onthionline.domain.ExamQuestion;
import vn.actvn.onthionline.service.dto.ExamDto;
import vn.actvn.onthionline.service.dto.ExamQuestionDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamMapper {
    @Autowired
    private ExamQuestionMapper examQuestionMapper;

    public ExamDto toDto(Exam exam) {
        if (exam == null)
            return null;

        ExamDto examDto = new ExamDto();
        examDto.setId(exam.getId());
        examDto.setImage(exam.getImage());
        examDto.setName(exam.getName());
        examDto.setDescription(exam.getDescription());
        examDto.setSubject(exam.getSubject());
        examDto.setTime(exam.getTime());
        List<ExamQuestionDto> examQuestionDtos = exam.getExamQuestions().stream().map(examQuestionMapper::toDto).collect(Collectors.toList());
        examDto.setExamQuestions(examQuestionDtos);
        return examDto;
    }

    public ExamDto toDtoWithQuestion(Exam exam, List<ExamQuestion> examQuestions) {
        if (exam == null)
            return null;

        ExamDto examDto = new ExamDto();
        examDto.setId(exam.getId());
        examDto.setImage(exam.getImage());
        examDto.setName(exam.getName());
        examDto.setDescription(exam.getDescription());
        examDto.setSubject(exam.getSubject());
        examDto.setTime(exam.getTime());
        List<ExamQuestionDto> examQuestionDtos = examQuestions.stream().map(examQuestionMapper::toDto).collect(Collectors.toList());
        examDto.setExamQuestions(examQuestionDtos);
        return examDto;
    }

    public Exam toEntity(ExamDto examDto) {
        if (examDto == null)
            return null;

        ModelMapper modelMapper = new ModelMapper();
        Exam exam = modelMapper.map(examDto,Exam.class);
        return exam;
    }
}
