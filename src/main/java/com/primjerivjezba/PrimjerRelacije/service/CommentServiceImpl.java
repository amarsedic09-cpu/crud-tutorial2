package com.primjerivjezba.PrimjerRelacije.service;

import com.primjerivjezba.PrimjerRelacije.exception.ResourceNotFoundException;
import com.primjerivjezba.PrimjerRelacije.model.Comment;
import com.primjerivjezba.PrimjerRelacije.model.Tutorial;
import com.primjerivjezba.PrimjerRelacije.repository.CommentRepository;
import com.primjerivjezba.PrimjerRelacije.repository.TutorialRepository;
import com.primjerivjezba.PrimjerRelacije.request.AddCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private TutorialRepository tutorialRepository;

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public Comment addComment(Comment request,Long tutorialId) {

         tutorialRepository.findById(tutorialId)
                .ifPresent(tutorial -> {
                    tutorial.getComments().add(request);
                    //request.setTutorial(tutorial);
                   // return commentRepository.save(request);
                tutorialRepository.save(tutorial);
                });
            return null;
             //   .orElseThrow(() -> new ResourceNotFoundException("Tutorial with this id does not exist"));



//    return tutorialRepository.findById(tutorialId)
//            .map(tutorial -> {
////                tutorial.addComment(request);
//                request.setTutorial(tutorial);
//
//                return commentRepository.save(request);
//            }
//            )
//            .orElseThrow(()-> new ResourceNotFoundException("Tutorijal sa ovim id nije pronadjen"));

    }

    @Override
    public Comment updateCOmment(Comment request, Long commentId) {

        return commentRepository.findById(commentId).
                map(comment -> {
                    comment.setContent(request.getContent());
                    return commentRepository.save(comment);
                })
                .orElseThrow(()-> new ResourceNotFoundException("Comment with this id not found"));


    }

    @Override
    public List<Comment> getAllComments(Long tutorialId) {

        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(()-> new ResourceNotFoundException("Tutorial with this id not found"));
        List<Comment> comments = new ArrayList<>();
        comments.addAll(tutorial.getComments());
        return comments;

    }

    @Override
    public void deleteCommentTutorialId(Long commentId, Long tutorialId) {

        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(()-> new ResourceNotFoundException("Tutorial with this id not found"));

        // napravili smo metodu da nadjemo comment od trazenog tutorijala
//        Comment commentToRemove = tutorial.getComments()
//                .stream()
//                .filter(comm -> comm.getId().equals(commentId))
//                .findFirst()
//                .orElseThrow(()-> new ResourceNotFoundException("Comment for tutorial not found"));

        //kada napravimo metodu za pronalazak commenta,
        //onda moramo pozvati nju
        Comment commentToRemove = getComment(commentId,tutorialId);

        tutorial.clearComment(commentToRemove);
        tutorialRepository.save(tutorial);


    }

    @Override
    public Comment getComment(Long commentId, Long TutorialId) {
        Tutorial tutorial = tutorialRepository.findById(TutorialId)
                .orElseThrow(()-> new ResourceNotFoundException("Tutorial with this id not found"));
        return tutorial.getComments()
                .stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Comment with this id not found")
                );
    }


}
