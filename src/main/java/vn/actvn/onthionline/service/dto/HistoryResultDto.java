package vn.actvn.onthionline.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResultDto {
    private Integer numAnswer;
    private Integer numCorrectAns;
    private Integer time;
    private Integer totalQuestion;
    private List<ExamQuestionResultDto> examQuestions;
}
