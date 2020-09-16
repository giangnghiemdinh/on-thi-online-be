package vn.actvn.onthionline.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamAnswer {
    private Integer questionId;
    private String answer;

    @Override
    public int hashCode() {
        return Objects.hash(questionId, answer);
    }

    @Override
    public String toString() {
        return "ExamAnswer{" +
                "questionId='" + questionId + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
