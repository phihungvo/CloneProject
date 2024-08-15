package com.luv2code.MiniProject.service;

import com.luv2code.MiniProject.payload.PostDto;
import com.luv2code.MiniProject.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(long id, PostDto postDto);

    void deletePostById(long id);

    List<PostDto> getPostsByCategory(long categoryId);
}
