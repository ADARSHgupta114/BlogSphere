package com.BlogSphere.post.repository;


import com.BlogSphere.post.entites.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface postRepository extends JpaRepository<Post,String> {
    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String titleKeyword, String contentKeyword);
}
