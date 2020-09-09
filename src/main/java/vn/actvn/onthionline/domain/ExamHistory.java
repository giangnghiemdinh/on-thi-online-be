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
    private Integer time;

    @Column(name = "num_correct_ans")
    private Integer numCorrectAns;

    @Column(name = "num_pause")
    private Integer numPause;

    @Column(name = "num_remake")
    private Integer numRemake;

    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userCreated;
}
