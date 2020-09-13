package vn.actvn.onthionline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.actvn.onthionline.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
