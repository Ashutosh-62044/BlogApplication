package com.blog.blogger.service.Impl;

import com.blog.blogger.entity.Comment;
import com.blog.blogger.entity.Post;
import com.blog.blogger.exception.ResourceNotFoundException;
import com.blog.blogger.payload.CommentDto;
import com.blog.blogger.repository.CommentRepository;
import com.blog.blogger.repository.PostRepository;
import com.blog.blogger.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // Use a single constructor to inject both repositories
    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        // Create a new Comment entity
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        // Convert the saved comment to a DTO
        CommentDto dto = new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setBody(savedComment.getBody());
        dto.setEmail(savedComment.getEmail());

        return dto;
    }

    @Override
    public void deleteComment(long commentId) {
        commentRepository.findById(commentId).orElseThrow
                (()-> new ResourceNotFoundException("comment is not found with id: " +commentId));
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtoList = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return commentDtoList;
    }
    CommentDto mapToDto(Comment comment)
    {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setBody(comment.getBody());
        dto.setEmail(comment.getEmail());

        return dto;
    }

    @Override
    public CommentDto updateComment(long commentId, CommentDto commentdto)
    {
        Comment comment = commentRepository.findById(commentId).orElseThrow
                (() -> new ResourceNotFoundException("comment is not fount with id: " + commentId));

        comment.setName(commentdto.getName());
        comment.setBody(commentdto.getBody());
        comment.setEmail(commentdto.getEmail());
        Comment updateComment = commentRepository.save(comment);

        CommentDto dto = mapToDto(updateComment);
        return dto;
    }



}
