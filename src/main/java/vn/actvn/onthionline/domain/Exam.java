package vn.actvn.onthionline.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam")
public class Exam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "grade")
    private String grade;      // Cấp

    @Column(name = "subject")
    private String subject;    // Môn học

    @Column(name = "num_question")
    private Integer numQuestion;   // Số câu hỏi

    @Column(name = "num_people_did")
    private Integer numPeopleDid;   // Số người làm bài

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "time")
    private Integer time;   // Thời gian làm bài

    @Column(name = "user_created")
    private String userCreated;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @OneToMany(mappedBy = "exam")
    private List<ExamQuestion> examQuestions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "exam")
    private List<ExamHistory> examHistory;

    @OneToMany(mappedBy = "exam")
    private List<Comment> comments = new ArrayList<>();

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, subject, numQuestion, numPeopleDid, description, isActive, time, userCreated, createdDate, updatedDate, examHistory, examQuestions, comments);
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", subject='" + subject + '\'' +
                ", numQuestion='" + numQuestion + '\'' +
                ", numPeopleDid='" + numPeopleDid + '\'' +
                ", description='" + description + '\'' +
                ", isActive='" + isActive + '\'' +
                ", time='" + time + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                '}';
    }
}
