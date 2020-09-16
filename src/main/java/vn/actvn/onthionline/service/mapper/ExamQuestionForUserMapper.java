package vn.actvn.onthionline.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.actvn.onthionline.domain.ExamQuestion;
import vn.actvn.onthionline.service.dto.ExamQuestionDto;
import vn.actvn.onthionline.service.dto.ExamQuestionForUserDto;

@Service
public class ExamQuestionForUserMapper {
    public ExamQuestionForUserDto toDto(ExamQuestion examQuestion) {
        if (examQuestion == null)
            return null;

        ModelMapper modelMapper = new ModelMapper();
        ExamQuestionForUserDto examQuestionDto = modelMapper.map(examQuestion,ExamQuestionForUserDto.class);
        return examQuestionDto;
    }
}
