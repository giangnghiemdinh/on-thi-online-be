package vn.actvn.onthionline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.actvn.onthionline.domain.ExamQuestion;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Integer>  {

    @Query(value = "from ExamQuestion e where e.id = :id and e.exam.id = :examId")
    Optional<ExamQuestion> findByIdAndExamId(@Param("id") Integer id, @Param("examId") Integer examId);
}
