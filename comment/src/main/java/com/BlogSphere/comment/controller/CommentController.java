package com.BlogSphere.comment.controller;


import com.BlogSphere.comment.entity.Comment;
import com.BlogSphere.comment.payload.commentDTO;
import com.BlogSphere.comment.payload.commentResponse;
import com.BlogSphere.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    //http://localhost:8082/api/comments
    @PostMapping("/save-comment")
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment){
        Comment c = commentService.saveComment(comment);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
    @GetMapping("{postId}")
    public List<commentResponse> getAllCommentsByPostId(@PathVariable String postId){
        List<commentResponse> comments = commentService.getAllCommentsByPostId(postId);
        return comments;
    }
    @PutMapping("/update-comment/{id}")
    public ResponseEntity<String> updateComment(@PathVariable String id, @RequestBody commentDTO dto){
        String response = commentService.UpdateComment(id,dto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/delete-comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable String commentId){
        String status = commentService.DeleteComment(commentId);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deleteCommentByPost(@PathVariable String postId){
        commentService.deleteCommentByPostId(postId);
        return new ResponseEntity<>("Comment Deleted",HttpStatus.OK);
    }

}
