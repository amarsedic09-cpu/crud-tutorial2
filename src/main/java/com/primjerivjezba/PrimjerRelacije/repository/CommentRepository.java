package com.primjerivjezba.PrimjerRelacije.repository;

import com.primjerivjezba.PrimjerRelacije.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
