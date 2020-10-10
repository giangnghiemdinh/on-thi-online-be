package vn.actvn.onthionline.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatResponse {
    private String text;
    private String imageSrc;
    private String link;

    @Override
    public String toString() {
        return "ChatResponse{" +
                "text='" + text + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                ", link='" + link + '\'' +
                '}';
    }


}
