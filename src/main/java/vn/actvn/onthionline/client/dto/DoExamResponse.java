package vn.actvn.onthionline.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.actvn.onthionline.service.dto.HistoryResultDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoExamResponse {
    private HistoryResultDto result;
}
