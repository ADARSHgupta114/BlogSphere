package com.BlogSphere.post.service;


import com.BlogSphere.post.config.RestTemplateConfig;
import com.BlogSphere.post.entites.Post;
import com.BlogSphere.post.exception.ResourceNotFound;
import com.BlogSphere.post.payload.PostDto;
import com.BlogSphere.post.payload.UpdatePostDto;
import com.BlogSphere.post.repository.postRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private postRepository postRepository;

    @Autowired
    private RestTemplateConfig restTemplate;

    public Post savePost(Post post){
        String postId = UUID.randomUUID().toString();
        post.setId(postId);
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    public Post findPostById(String postId) {
       Post post = postRepository.findById(postId).get();
       return post;
    }

    public PostDto getPostWithComments(String postId) {
        Post post = postRepository.findById(postId).get();
        ArrayList comments = restTemplate.getRestTemplate().getForObject("http://localhost:8082/api/comments/" + postId, ArrayList.class);
        PostDto postDto = new PostDto();
        postDto.setPostId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        postDto.setComments(comments);
        return postDto;
    }

    public void deletePostServ(String id) {
        postRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Post Not Found"));
        restTemplate.getRestTemplate().delete("COMMENT-SERVICE/api/comments/delete/" + id);
        postRepository.deleteById(id);
    }
    public List<Post> searchkeyword(String keyword){
        return postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword,keyword);
    }
    public String UpdatePost(String id, UpdatePostDto postdto) {
    if(postRepository.findById(id).isPresent()){
        Post post = postRepository.findById(id).get();
            if(postdto.getContent()!=null)
                post.setContent(postdto.getContent());
            if(postdto.getDescription()!=null)
                post.setDescription(postdto.getDescription());
            if(postdto.getTitle()!=null)
                post.setTitle(postdto.getTitle());
        postRepository.save(post);
        return "Post Updated Please Check";
    }
    return "Post Not Found";
    }

    public List<PostDto> GetAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> dto = posts.stream().map(x-> getPostWithComments(x.getId())).collect(Collectors.toList());
       return dto;
    }
}
