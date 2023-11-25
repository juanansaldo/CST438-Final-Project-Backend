package com.cst438.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Actor;
import com.cst438.domain.ActorRepository;
import com.cst438.domain.User;
import com.cst438.domain.UserRepository;
import com.cst438.dto.ActorDTO;

@RestController
@CrossOrigin
public class ActorsController {

    @Autowired
    private ActorRepository actorRepository;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/actors")
    public List<ActorDTO> getAllActors() {
        
        Iterable<Actor> actors = actorRepository.findAll();
        List<ActorDTO> actorList = new ArrayList<>();

        // Convert each Actor to ActorDTO and add to the list
        for (Actor a : actors) {
            ActorDTO d = new ActorDTO(a.getActorId(), a.getName(), a.getDOB(), a.getPortrait(), a.getAbout());
            actorList.add(d);
        }

        // Return the list of ActorDTOs
        return actorList;
    }
    
    @GetMapping("/actors/{id}")
    public ActorDTO getActor(@PathVariable("id") int id) {
    	
        // Find a actor by their ID in the repository
        Optional<Actor> actorOptional = actorRepository.findById(id);

        if (actorOptional.isPresent()) {
            // If the actor is found, convert it to a ActorDTO and return
            Actor a = actorOptional.get();
            return new ActorDTO(a.getActorId(), a.getName(), a.getDOB(), a.getPortrait(), a.getAbout());
        } else {
            // If the actor is not found, throw an error
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found.");
        }
    }
    
    @PostMapping("/actors")
    public int createActor(@RequestBody ActorDTO actorDTO, Principal principal) {
    	
    	String username = principal.getName();
    	
    	User user = userRepository.findByUsername(username);
    	
        if (user == null || !user.getRole().equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not an admin or not found.");
        }
    	
        // Create a new actor
        Actor a = new Actor();
        a.setName(actorDTO.name());
        a.setDOB(actorDTO.dob());
        a.setPortrait(actorDTO.portrait());
        a.setAbout(actorDTO.about());
        
        // Save the new actor to the database
        actorRepository.save(a);

        // Return the ID of the newly created actor
        return a.getActorId();
    }
    
    @PutMapping("/actors/{id}")
    public void updateActor(@PathVariable("id") int id, @RequestBody ActorDTO actorDTO, Principal principal) {
        
    	String username = principal.getName();
    	
    	User user = userRepository.findByUsername(username);
    	
        if (user == null || !user.getRole().equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not an admin or not found.");
        }
    	
    	// Check if the actor exists in the repository
        Optional<Actor> actorOptional = actorRepository.findById(id);

        if (!actorOptional.isPresent()) {
            // If the actor is not found, throw an error
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found.");
        }

        Actor actor = actorOptional.get();

        // Update the actor details
        actor.setName(actorDTO.name());
        actor.setDOB(actorDTO.dob());
        actor.setPortrait(actorDTO.portrait());
        actor.setAbout(actorDTO.about());

        // Save the updated actor to the database
        actorRepository.save(actor);
    }
    
    @DeleteMapping("/actors/{id}")
    public void deleteActor(@PathVariable("id") int id, Principal principal) {
    	
    	String username = principal.getName();
    	
    	User user = userRepository.findByUsername(username);
    	
        if (user == null || !user.getRole().equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not an admin or not found.");
        }
    	
    	// Check if the actor exists in the repository
        Optional<Actor> actorOptional = actorRepository.findById(id);

        if (!actorOptional.isPresent()) {
            // If the actor is not found, throw an error
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Actor not found.");
        }

        Actor actor = actorOptional.get();

        actorRepository.delete(actor);
    }
}
