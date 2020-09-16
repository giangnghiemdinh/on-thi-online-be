package vn.actvn.onthionline.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamInfoDto {
    private Integer id;
    private String name;
    private String image;
    private String subject;
    private String description;
    private Integer numQuestion;
    private Integer numPeopleDid;
    private Integer time;
    private ExamHistoryDto lastHistory;
}
