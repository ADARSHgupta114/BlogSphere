package com.BlogSphere.comment.service;


import com.BlogSphere.comment.config.RestTemplateConfig;
import com.BlogSphere.comment.entity.Comment;
import com.BlogSphere.comment.exception.ResouceNotFound;
import com.BlogSphere.comment.payload.Post;
import com.BlogSphere.comment.payload.commentDTO;
import com.BlogSphere.comment.payload.commentResponse;
import com.BlogSphere.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RestTemplateConfig restTemplate;

    public Comment saveComment(Comment comment){
        Post post = restTemplate.getRestTemplate().getForObject("http://POST-SERVICE/api/posts/"+comment.getPostId(), Post.class);

        if(post!=null){
            String commentId = UUID.randomUUID().toString();
            comment.setCommentId(commentId);
            Comment savedComment = commentRepository.save(comment);
            return savedComment;
        }else{
            return null;
        }
    }
    public List<commentResponse> getAllCommentsByPostId(String postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<commentResponse> res = comments.stream().map(this::mapComments).collect(Collectors.toList());
        return res;
    }
    public commentResponse mapComments(Comment comment){
        commentResponse response = new commentResponse();
        response.setBody(comment.getBody());
        response.setName(comment.getName());
        response.setEmail(comment.getEmail());
        return response;
    }

    public String DeleteComment(String commentId) {
        if(commentRepository.findById(commentId).isPresent()){
            commentRepository.deleteById(commentId);
            return "Comment Deleted Successfully";
        }
        return "Comment Not Found";
    }

    public String UpdateComment(String id, commentDTO dto) {
        if(commentRepository.findById(id).isPresent()){
            Comment comment = commentRepository.findById(id).get();
            comment.setBody(dto.getBody());
            commentRepository.save(comment);
            return "Comment Updated";
        }
        return "Internal Server Error";
    }

    public void deleteCommentByPostId(String postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
      //  comments.stream().map(comment -> comment.getCommentId()).forEach(id->commentRepository.deleteById(id));
        comments.forEach(x->commentRepository.deleteById(x.getCommentId()));
    }

}
