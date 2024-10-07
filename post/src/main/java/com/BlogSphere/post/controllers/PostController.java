package com.BlogSphere.post.controllers;
import com.BlogSphere.post.entites.Post;
import com.BlogSphere.post.payload.PostDto;
import com.BlogSphere.post.payload.UpdatePostDto;
import com.BlogSphere.post.service.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping("/home-page")
    public ResponseEntity<List<PostDto>> getAllPost(){
        List<PostDto> allposts = postService.GetAllPosts();
        return new ResponseEntity<>(allposts,HttpStatus.OK);
    }
    @PostMapping("/save-post")
    public ResponseEntity<Post> savePost(@RequestBody Post post){
        Post newPost = postService.savePost(post);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }
//http://localhost:8081/api/posts/{postId}
    @GetMapping("/{postId}")
    public Post getPostByPostId(@PathVariable String postId){
        Post post = postService.findPostById(postId);
        return post;
    }

   // http://localhost:8081/api/posts/{postId}/comments
    @GetMapping("/{postId}/comments")
    @CircuitBreaker(name = "commentBreaker", fallbackMethod = "commentFallback")
    public ResponseEntity<PostDto> getPostWithComments(@PathVariable String postId){
        PostDto postDto = postService.getPostWithComments(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
    @PostMapping("/search/q={keyword}")
    public ResponseEntity<List<Post>> searchPost(@PathVariable String keyword){
        List<Post> posts=postService.searchkeyword(keyword);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }
    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity<String> DeletePostById(@PathVariable String id){
        postService.deletePostServ(id);
        return new ResponseEntity<>("Post Deleted SuccessFully",HttpStatus.OK);
    }
    @PutMapping("/update-post/{id}")
    public ResponseEntity<String> updatePost(@PathVariable String id,@RequestBody UpdatePostDto postdto){
    String response = postService.UpdatePost(id,postdto);
    return new ResponseEntity<>(response,HttpStatus.OK);
    }
    public ResponseEntity<PostDto> commentFallback(String postId, Exception ex) {
        System.out.println("Fallback is executed because service is down : "+ ex.getMessage());

        ex.printStackTrace();

        PostDto dto = new PostDto();
        dto.setPostId("1234");
        dto.setTitle("Service Down");
        dto.setContent("Service Down");
        dto.setDescription("Service Down");

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }



}
