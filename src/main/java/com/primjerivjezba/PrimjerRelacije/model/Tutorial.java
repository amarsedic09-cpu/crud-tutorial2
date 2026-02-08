package com.primjerivjezba.PrimjerRelacije.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.primjerivjezba.PrimjerRelacije.request.AddCommentRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tutorials")
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private  String title;

    private String description;

    @OneToMany(mappedBy = "tutorial", cascade = CascadeType.ALL,orphanRemoval = true)
    //@JoinColumn(name = "tutorial_id")
    //@JsonManagedReference

    private Set<Comment> comments = new HashSet<>();

    public Tutorial(Long id, String title, String description) {
    }

    public Tutorial(String title, String description) {
    }

    public Tutorial(Tutorial tutorial) {
    }

    // Convenience methods for bidirectional association management
    public void addComment(Comment comment){
        comments.add(comment);
        comment.setTutorial(this);
        //comment.setTutorial(comment.getTutorial());
    }

    public void clearComment(Comment comment){
        comments.remove(comment);
        comment.setTutorial(null);
    }
}
