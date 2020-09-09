package vn.actvn.onthionline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.actvn.onthionline.domain.ExamHistory;

public interface ExamHistoryRepository extends JpaRepository<ExamHistory, Integer> {
}
