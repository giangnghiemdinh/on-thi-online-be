package vn.actvn.onthionline.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.actvn.onthionline.domain.ExamAnswer;
import vn.actvn.onthionline.domain.ExamHistory;
import vn.actvn.onthionline.service.dto.ExamQuestionResultDto;
import vn.actvn.onthionline.service.dto.HistoryResultDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryResultMapper {
    @Autowired
    private ExamQuestionResultMapper examQuestionResultMapper;

    public HistoryResultDto toDto(ExamHistory history) {
        HistoryResultDto historyResultDto = new HistoryResultDto();
        historyResultDto.setNumAnswer(history.getNumAns());
        historyResultDto.setNumCorrectAns(history.getNumCorrectAns());
        historyResultDto.setDoTime(history.getTime());
        historyResultDto.setTotalQuestion(history.getExam().getNumQuestion());
        historyResultDto.setTotalTime(history.getExam().getTime());
        historyResultDto.setExamName(history.getExam().getName());
        historyResultDto.setExamDescription(history.getExam().getDescription());

        List<ExamQuestionResultDto> examQuestionResultDtos =
                history.getExam().getExamQuestions().stream().map(examQuestionResultMapper::toDto).collect(Collectors.toList());
        examQuestionResultDtos.stream().forEach(question -> {
            question.setAnswer("");
            for (ExamAnswer answer : history.getExamAnswers()) {
                if (question.getId() == answer.getQuestionId()) {
                    question.setAnswer(answer.getAnswer());
                    break;
                }
            }
        });
        historyResultDto.setExamQuestions(examQuestionResultDtos);
        return historyResultDto;
    }
}
