package vn.actvn.onthionline.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam_history")
public class ExamHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "time")
    private Integer time;             // Thời gian làm bài

    @Column(name = "num_correct_ans")
    private Integer numCorrectAns;    // Số câu trả lời đúng

    @Column(name = "num_pause")
    private Integer numPause;         // Số lần tạm dừng

    @Column(name = "num_remake")
    private Integer numRemake;        // Số lần làm lại

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userCreated;

    @OneToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;
}
