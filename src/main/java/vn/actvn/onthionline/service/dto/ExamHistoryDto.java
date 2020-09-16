package vn.actvn.onthionline.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamHistoryDto {
    private Integer id;
    private String name;
    private Integer time;
    private Integer numCorrectAns;
    private Integer numAns;
    private Integer totalQuestion;
    private Date createdDate;
}
