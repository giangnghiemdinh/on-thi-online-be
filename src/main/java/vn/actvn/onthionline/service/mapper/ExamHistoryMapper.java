package vn.actvn.onthionline.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.actvn.onthionline.domain.ExamAnswer;
import vn.actvn.onthionline.domain.ExamHistory;
import vn.actvn.onthionline.service.dto.ExamHistoryDto;
import vn.actvn.onthionline.service.dto.ExamQuestionResultDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamHistoryMapper {
    @Autowired
    private ExamQuestionResultMapper examQuestionResultMapper;

    public ExamHistoryDto toDto(ExamHistory history) {
        ExamHistoryDto historyResultDto = new ExamHistoryDto();
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
