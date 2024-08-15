package com.luv2code.MiniProject.controller;

import com.luv2code.MiniProject.payload.PostDto;
import com.luv2code.MiniProject.payload.PostResponse;
import com.luv2code.MiniProject.service.PostService;
import com.luv2code.MiniProject.utils.AppConstants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/posts")
public class PostController {

    PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/postId")
    public ResponseEntity<PostDto> getPostsById(@PathVariable long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PutMapping("/postId")
    public ResponseEntity<PostDto> updatePost(@PathVariable long postId, @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.updatePost(postId, postDto), HttpStatus.OK);
    }

    @DeleteMapping("/postId")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable long categoryId){
        List<PostDto> postDtoList = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }
}
