package com.luv2code.MiniProject.service.impl;

import com.luv2code.MiniProject.entity.Category;
import com.luv2code.MiniProject.entity.Post;
import com.luv2code.MiniProject.exception.BlogApiException;
import com.luv2code.MiniProject.exception.ResourceNotFoundException;
import com.luv2code.MiniProject.payload.PostDto;
import com.luv2code.MiniProject.payload.PostResponse;
import com.luv2code.MiniProject.repository.CategoryRepository;
import com.luv2code.MiniProject.repository.PostRepository;
import com.luv2code.MiniProject.service.PostService;
import lombok.AccessLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostServiceImpl implements PostService {

    PostRepository postRepository;

    CategoryRepository categoryRepository;

    ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        Post post = mapToEntity(postDto);
        post.setCategory(category);

        return mapToDto(postRepository.save(post));
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        //listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        List<PostDto> content = listOfPosts.stream().map(this::mapToDto).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(postResponse.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        if(!post.getCategory().getId().equals(category.getId()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Post does not belong to category");

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        return mapToDto(postRepository.save(post));
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Post> postList = postRepository.findByCategoryId(categoryId);
        //postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return postList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private PostDto mapToDto(Post post){
        return modelMapper.map(post, PostDto.class);
    }

    private Post mapToEntity(PostDto postDto){
        return modelMapper.map(postDto, Post.class);
    }
}
