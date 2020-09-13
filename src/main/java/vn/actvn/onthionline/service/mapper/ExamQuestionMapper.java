package vn.actvn.onthionline.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.actvn.onthionline.domain.ExamQuestion;
import vn.actvn.onthionline.service.dto.ExamQuestionDto;

@Service
public class ExamQuestionMapper {
    public ExamQuestion toEntity(ExamQuestionDto examQuestionDto) {
        if (examQuestionDto == null)
            return null;

        ModelMapper modelMapper = new ModelMapper();
        ExamQuestion examQuestion = modelMapper.map(examQuestionDto,ExamQuestion.class);
        return examQuestion;
    }

    public ExamQuestionDto toDto(ExamQuestion examQuestion) {
        if (examQuestion == null)
            return null;

        ModelMapper modelMapper = new ModelMapper();
        ExamQuestionDto examQuestionDto = modelMapper.map(examQuestion,ExamQuestionDto.class);
        return examQuestionDto;
    }
}
