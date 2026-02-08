package com.primjerivjezba.PrimjerRelacije.service;

import com.primjerivjezba.PrimjerRelacije.exception.AlreadyExistsException;
import com.primjerivjezba.PrimjerRelacije.exception.ResourceNotFoundException;
import com.primjerivjezba.PrimjerRelacije.model.Tutorial;
import com.primjerivjezba.PrimjerRelacije.repository.TutorialRepository;
import com.primjerivjezba.PrimjerRelacije.request.AddTutorialRequest;
import com.primjerivjezba.PrimjerRelacije.request.UpdateTutorialRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TutorialServiceImpl implements  TutorialService{


    @Autowired
    private final TutorialRepository tutorialRepository;

    @Override
    public Tutorial addTutorial(AddTutorialRequest request) {


//        if (tutorialExists(request.getTitle())){
//            throw new AlreadyExistsException(request.getTitle() + " already exists Tutorial with this name, zou may update it only");
//        }
//
//        Tutorial tutorial = new Tutorial();
//        tutorial.setTitle(request.getTitle());
//        tutorial.setDescription(request.getDescription());
//        return tutorialRepository.save(tutorial);

//       return tutorialRepository.save(createTutorial(request));

        return Optional.of(request)
                .filter(tutorial->
                        (!tutorialRepository.existsByTitle(request.getTitle())))
                .map(req ->{
                    Tutorial tut = new Tutorial();
                    tut.setTitle(request.getTitle());
                    tut.setDescription(request.getDescription());
                    return tutorialRepository.save(tut);
                })
                .orElseThrow(()-> new AlreadyExistsException("tutorial sa ovim nayivom vec postoji"));
    }

    public boolean tutorialExists(String title) {
        return tutorialRepository.existsByTitle(title);
    }

    private Tutorial createTutorial(AddTutorialRequest request){
        return new Tutorial(

                request.getTitle(),
                request.getDescription()

        );
    }

    @Override
    public Tutorial getTutorialById(Long tutorialId) {
        return tutorialRepository.findById(tutorialId).
                orElseThrow(()-> new ResourceNotFoundException("Tutorial not found with this id"));
    }

    @Override
    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }

    @Override
    public Tutorial updateTutorial(UpdateTutorialRequest request, Long tutorialId) {
        return tutorialRepository.findById(tutorialId)
                .map(existingTutorial -> updateExistingTutorial(existingTutorial,request))
               // .map(existingTutorial->tutorialRepository.save(existingTutorial))
                //zamijenimo lambda function sa method referance
                .map(tutorialRepository::save)
                .orElseThrow(()-> new ResourceNotFoundException("Tutorial not found with this id"));
    }


    private Tutorial updateExistingTutorial(Tutorial existingTutorial, UpdateTutorialRequest request){

        existingTutorial.setTitle(request.getTitle());
        existingTutorial.setDescription(request.getDescription());

        return existingTutorial;
    }

    @Override
    public List<Tutorial> getTutorialByTitle(String title) {
        return tutorialRepository.findByTitle(title);
    }

    @Override
    public void deleteTutorialById(Long tutorialId) {
         tutorialRepository.findById(tutorialId)
                .ifPresentOrElse(tutorial -> tutorialRepository.delete(tutorial)

                        //(tutorialRepository::delete) ili sa method reference
                        , () -> {
                            throw new ResourceNotFoundException("Tutorial not found");}
                );
    }

    @Override
    public Long countTutorialsByTitle(String title) {
        return tutorialRepository.countByTitle(title);
    }


}
