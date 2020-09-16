package vn.actvn.onthionline.service.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.actvn.onthionline.domain.ExamHistory;
import vn.actvn.onthionline.service.dto.ExamHistoryDto;

@Service
public class ExamHistoryMapper {
    public ExamHistoryDto toDto(ExamHistory examHistory) {
        if (examHistory == null)
            return null;

        ModelMapper modelMapper = new ModelMapper();
        ExamHistoryDto examHistoryDto = modelMapper.map(examHistory,ExamHistoryDto.class);
        examHistoryDto.setTotalQuestion(examHistory.getExam().getNumQuestion());
        return examHistoryDto;
    }
}
