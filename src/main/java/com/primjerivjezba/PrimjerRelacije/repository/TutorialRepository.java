package com.primjerivjezba.PrimjerRelacije.repository;

import com.primjerivjezba.PrimjerRelacije.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    boolean existsByTitle(String title);

    List<Tutorial> findByTitle(String title);

    Long countByTitle(String title);

}
