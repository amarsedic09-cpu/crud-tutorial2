package com.primjerivjezba.PrimjerRelacije.controller;

import com.primjerivjezba.PrimjerRelacije.exception.AlreadyExistsException;
import com.primjerivjezba.PrimjerRelacije.exception.ResourceNotFoundException;
import com.primjerivjezba.PrimjerRelacije.model.Tutorial;
import com.primjerivjezba.PrimjerRelacije.request.AddTutorialRequest;
import com.primjerivjezba.PrimjerRelacije.request.UpdateTutorialRequest;
import com.primjerivjezba.PrimjerRelacije.response.ApiResponse;
import com.primjerivjezba.PrimjerRelacije.service.TutorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/tutorial")
public class TutorialController {

    private final TutorialService tutorialService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTutorial(@RequestBody AddTutorialRequest request){

        try {
            Tutorial theTutorial = tutorialService.addTutorial(request);
            return ResponseEntity.ok(new ApiResponse("Tutorial added successfully", theTutorial));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));


        }
    }

    @GetMapping("/{tutorialId}/tutorial")
    public ResponseEntity<ApiResponse> getTutorialById(@PathVariable Long tutorialId){

        try {
            Tutorial theTutorial = tutorialService.getTutorialById(tutorialId);
            return ResponseEntity.ok(new ApiResponse("Tutorial with this id found", theTutorial));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllTutorials(){

        try {
            List<Tutorial> listTutorials = tutorialService.getAllTutorials();
            if(CollectionUtils.isEmpty(listTutorials)){
                return ResponseEntity.ok(new ApiResponse("There are no tutorials", List.of()));
            }

            return ResponseEntity.ok(new ApiResponse("List of tutorials found", listTutorials));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{tutorialId}/update")
    public ResponseEntity<ApiResponse> updateTutorial(@RequestBody UpdateTutorialRequest request,
                                                      @PathVariable Long tutorialId){

        try {
            Tutorial updatedTutorial = tutorialService.updateTutorial(request, tutorialId);
            return ResponseEntity.ok(new ApiResponse("Tutorial with this id updated successfully", updatedTutorial));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/by/title")
    public ResponseEntity<ApiResponse> getTutorialByTitle(@RequestParam String title){

        try {
            List<Tutorial> getTutorials = tutorialService.getTutorialByTitle(title);
            if(getTutorials == null || getTutorials.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Tutorial with this title not found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Tutorial with this title found", getTutorials));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteTutorialById(@PathVariable ("id") Long tutorialId){

        try {
            tutorialService.deleteTutorialById(tutorialId);
            return ResponseEntity.ok(new ApiResponse("Tutorial with this id deleted successfully", tutorialId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/count/by-title")
    public ResponseEntity<ApiResponse> countTutorialsByTitle(@RequestParam String title){

        try {
            Long tutorialCount = tutorialService.countTutorialsByTitle(title);
            return ResponseEntity.ok(new ApiResponse("Count of tutorilas by title is ", tutorialCount));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }
}
