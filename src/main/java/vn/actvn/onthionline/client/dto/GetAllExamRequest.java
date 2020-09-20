package vn.actvn.onthionline.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllExamRequest {
    private Integer pageNumber;
    private Integer pageSize;
    private String name;
    private String subject;
    private String grade;
    private Boolean isActive;
}
