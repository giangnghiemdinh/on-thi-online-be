package vn.actvn.onthionline.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDto {
    private Integer id;
    private String name;
    private String image;
    private String subject;
    private String description;
    private Integer time;
    private List<ExamQuestionDto> examQuestions;
}