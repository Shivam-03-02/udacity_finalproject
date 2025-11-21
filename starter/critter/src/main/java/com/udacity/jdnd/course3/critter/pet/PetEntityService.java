package com.udacity.jdnd.course3.critter.pet;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetEntityService {

    @Autowired
    private PetRepository petRepository;

    

    public Pet save(Pet p){
        return petRepository.save(p);
    }

    public Pet findById(Long id){
        Optional<Pet> o = petRepository.findById(id);
        return o.orElse(null);
    }

    public List<Pet> findAll(){
        return petRepository.findAll();
    }

    public List<Pet> findByOwnerId(Long ownerId){
        return petRepository.findByOwnerId(ownerId);
    }
}
