package com.blog.blogger.controller;

import com.blog.blogger.entity.Post;
import com.blog.blogger.payload.CommentDto;
import com.blog.blogger.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Create new comment on post and saved the data base
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestParam("postId") long postId,
                                                    @RequestBody CommentDto commentDto)
    {
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // delete comment
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId)
    {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("comment is deleted", HttpStatus.OK);
    }

    // get all comment to display
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>>getCommentsByPostId(@PathVariable long postId){
        List<CommentDto> commentDto = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    // update comment by commentId

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long commentId , @RequestBody CommentDto commentdto)
    {
        CommentDto dto = commentService.updateComment(commentId, commentdto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
