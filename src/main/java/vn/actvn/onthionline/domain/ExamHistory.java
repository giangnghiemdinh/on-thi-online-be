package vn.actvn.onthionline.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.actvn.onthionline.repository.converter.ExamAnswerConverter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam_history")
public class ExamHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "time")
    private Integer time;             // Thời gian làm bài

    @Column(name = "num_correct_ans")
    private Integer numCorrectAns;    // Số câu trả lời đúng

    @Column(name = "num_ans")
    private Integer numAns;    // Số câu trả lời

    @Column(name = "exam_answer")
    @Lob
    @Convert(converter = ExamAnswerConverter.class)
    private List<ExamAnswer> examAnswers;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userCreated;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, time, numAns, numCorrectAns, examAnswers, createdDate, userCreated, exam);
    }

    @Override
    public String toString() {
        return "ExamHistory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", numAns='" + numAns + '\'' +
                ", numCorrectAns='" + numCorrectAns + '\'' +
                ", examAnswers='" + examAnswers + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", userCreated='" + userCreated + '\'' +
                '}';
    }
}
