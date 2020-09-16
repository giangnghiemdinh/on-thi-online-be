package vn.actvn.onthionline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.actvn.onthionline.domain.Exam;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer>  {

    @Query(value = "from Exam e where e.subject like :subject and e.isActive = true")
    Optional<List<Exam>> findAllExamActiveBySubject(@Param("subject") String subject);

    @Query(value = "from Exam e where e.id = :id and e.isActive = true")
    Optional<Exam> findExamActiveById(@Param("id") Integer id);

    @Query(value = "from Exam e where e.id = :id")
    Optional<Exam> findById(@Param("id") Integer id);

    List<Exam> findAll();
}
