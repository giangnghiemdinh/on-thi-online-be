package vn.actvn.onthionline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.actvn.onthionline.domain.Exam;
import vn.actvn.onthionline.domain.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer>  {
    Optional<List<Exam>> findAllBySubject(String subject);

    Optional<Exam> findById(Integer id);
}
