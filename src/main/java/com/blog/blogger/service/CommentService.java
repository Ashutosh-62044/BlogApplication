package com.blog.blogger.service;

import com.blog.blogger.payload.CommentDto;

import java.util.List;

public interface CommentService {
     CommentDto createComment(long postId, CommentDto commentDto);

     void deleteComment(long commentId);

     List<CommentDto> getCommentsByPostId(long postId);

     CommentDto updateComment(long commentId, CommentDto commentdto);
}
