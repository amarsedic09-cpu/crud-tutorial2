package com.primjerivjezba.PrimjerRelacije.controller;

import com.primjerivjezba.PrimjerRelacije.exception.ResourceNotFoundException;
import com.primjerivjezba.PrimjerRelacije.model.Comment;
import com.primjerivjezba.PrimjerRelacije.request.AddCommentRequest;
import com.primjerivjezba.PrimjerRelacije.response.ApiResponse;
import com.primjerivjezba.PrimjerRelacije.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{tutorialId}/add-comment")
    public ResponseEntity<ApiResponse> createComment(@PathVariable Long tutorialId,
                                                     @RequestBody Comment request){

        try {
            Comment theComment = commentService.addComment(request, tutorialId);
            return ResponseEntity.ok(new ApiResponse("Comment for tutorial with this id added", theComment));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{commentId}/update")
    public ResponseEntity<ApiResponse> updateCommentById(@PathVariable Long commentId,
                                                         @RequestBody Comment request){

        try {
            Comment theComment = commentService.updateCOmment(request,commentId);
            return ResponseEntity.ok(new ApiResponse("Comment with this id updated", commentId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/tutorials/{tutorialId}/all-comments")
    public ResponseEntity<ApiResponse> getAllCOmmentsForTutorial(@PathVariable Long tutorialId){

        try {
            List<Comment> coments = commentService.getAllComments(tutorialId);
            return ResponseEntity.ok(new ApiResponse("All comments for specified tutorial", coments));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/tutorials/{tutorialId}/comment/{commentId}/remove")
    public ResponseEntity<ApiResponse> deleteComemntForTutorial(@PathVariable Long tutorialId,
                                                                @PathVariable Long commentId){

        try {
            commentService.deleteCommentTutorialId(commentId,tutorialId);
            return ResponseEntity.ok(new ApiResponse("Comment deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
