package vn.actvn.onthionline.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletedExamDto {
    private Integer id;
    private ExamHistoryDto lastHistory;
    private String name;
}
