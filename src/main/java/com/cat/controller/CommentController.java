package com.cat.controller;

import com.cat.model.dao.Comment;
import com.cat.model.dto.Comment.ResponseComment;
import com.cat.model.dto.Comment.CreateCommentDTO;
import com.cat.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    @GetMapping("/comments")
    public ResponseEntity<Page<Comment>> getAllComments(Pageable pageable) {
        return ResponseEntity.ok().body(commentService.getAllComments(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable(value = "id") UUID commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok().body(comment);
    }

    @PostMapping
    public ResponseComment createComment(@RequestBody CreateCommentDTO createCommentDTO ) {
        return commentService.createComment(createCommentDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable(value = "id") UUID commentId,
                                                 @RequestBody Comment commentDetails) {
        final Comment updatedComment = commentService.updateComment(commentId, commentDetails);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(value = "id") UUID commentId) {
        commentService.deleteCommentById(commentId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/goal/{id}")
    public ResponseEntity<List<ResponseComment>> getGPSByEmployeeId(@PathVariable(value = "id") UUID goalId) {
        List<ResponseComment> comments = commentService.getAllCommentsByGoal(goalId);
        return ResponseEntity.ok().body(comments);
    }
}


