package vn.actvn.onthionline.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.actvn.onthionline.domain.ExamQuestion;
import vn.actvn.onthionline.service.dto.ExamQuestionDto;
import vn.actvn.onthionline.service.dto.ExamQuestionResultDto;

@Service
public class ExamQuestionResultMapper {
    public ExamQuestionResultDto toDto(ExamQuestion examQuestion) {
        if (examQuestion == null)
            return null;

        ModelMapper modelMapper = new ModelMapper();
        ExamQuestionResultDto examQuestionDto = modelMapper.map(examQuestion,ExamQuestionResultDto.class);
        return examQuestionDto;
    }
}
