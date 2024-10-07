package com.BlogSphere.comment.repository;

import com.BlogSphere.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,String> {
    List<Comment> findByPostId(String postId);
    void deleteAllByPostId(String postId);
}
