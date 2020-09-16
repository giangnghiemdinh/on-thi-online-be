package vn.actvn.onthionline.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.actvn.onthionline.domain.ExamAnswer;
import vn.actvn.onthionline.domain.ExamQuestion;
import vn.actvn.onthionline.service.dto.ExamAnswerDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamAnswerMapper {

    public List<ExamAnswer> toListEntity(List<ExamAnswerDto> examAnswerDtos) {
        if (examAnswerDtos.size() == 0) return null;

        List<ExamAnswer> examAnswers = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (ExamAnswerDto examAnswerDto : examAnswerDtos) {
            examAnswers.add(modelMapper.map(examAnswerDto,ExamAnswer.class));
        }
        return examAnswers;
    }
}
