package com.primjerivjezba.PrimjerRelacije.service;

import com.primjerivjezba.PrimjerRelacije.model.Tutorial;
import com.primjerivjezba.PrimjerRelacije.request.AddTutorialRequest;
import com.primjerivjezba.PrimjerRelacije.request.UpdateTutorialRequest;

import java.util.List;

public interface TutorialService {

    Tutorial addTutorial(AddTutorialRequest request);

    Tutorial getTutorialById(Long tutorialId);

    List<Tutorial> getAllTutorials();

    Tutorial updateTutorial(UpdateTutorialRequest request, Long tutorialId);

    List<Tutorial> getTutorialByTitle(String title);

    void deleteTutorialById(Long tutorialId);

    Long countTutorialsByTitle(String title);
}
