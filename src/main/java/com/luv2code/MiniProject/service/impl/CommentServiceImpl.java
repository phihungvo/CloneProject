package com.luv2code.MiniProject.service.impl;

import com.luv2code.MiniProject.entity.Comment;
import com.luv2code.MiniProject.entity.Post;
import com.luv2code.MiniProject.exception.BlogApiException;
import com.luv2code.MiniProject.exception.ResourceNotFoundException;
import com.luv2code.MiniProject.payload.CommentDto;
import com.luv2code.MiniProject.repository.CommentRepository;
import com.luv2code.MiniProject.repository.PostRepository;
import com.luv2code.MiniProject.service.CommentService;
import lombok.AccessLevel;
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
public class CommentServiceImpl implements CommentService {

    PostRepository postRepository;

    CommentRepository commentRepository;

    ModelMapper modelMapper;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        return modelMapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto){
        return modelMapper.map(commentDto, Comment.class);
    }
}
