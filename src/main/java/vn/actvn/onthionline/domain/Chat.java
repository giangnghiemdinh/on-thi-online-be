package vn.actvn.onthionline.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.actvn.onthionline.repository.converter.ExamAnswerConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat")
public class Chat implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="message")
    @Lob
    @Convert(converter = ExamAnswerConverter.class)
    private ChatMessage message;

    @Column(name="intern")
    private String intern;

    @Column(name="response")
    @Lob
    @Convert(converter = ExamAnswerConverter.class)
    private ChatResponse response;

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", intern='" + intern + '\'' +
                ", response='" + response + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        Chat chat = (Chat) o;
        return Objects.equals(getId(), chat.getId()) &&
                Objects.equals(getMessage(), chat.getMessage()) &&
                Objects.equals(getIntern(), chat.getIntern()) &&
                Objects.equals(getResponse(), chat.getResponse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMessage(), getIntern(), getResponse());
    }
}
