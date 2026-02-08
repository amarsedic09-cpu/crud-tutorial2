package com.primjerivjezba.PrimjerRelacije.service;

import com.primjerivjezba.PrimjerRelacije.model.Comment;
import com.primjerivjezba.PrimjerRelacije.request.AddCommentRequest;

import java.util.List;

public interface CommentService {

    Comment addComment(Comment request, Long tutorialId);

    Comment updateCOmment(Comment request, Long commentId);

    List<Comment> getAllComments(Long tutorialId);

    void deleteCommentTutorialId(Long commentId, Long tutorialId);

    Comment getComment(Long commentId,Long TutorialId);

}
