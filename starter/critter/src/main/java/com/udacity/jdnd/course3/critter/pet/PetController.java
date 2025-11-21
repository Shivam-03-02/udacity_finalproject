package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import com.udacity.jdnd.course3.critter.util.DTOMapper;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private com.udacity.jdnd.course3.critter.pet.PetEntityService petEntityService;

    @Autowired
    private com.udacity.jdnd.course3.critter.user.CustomerEntityService customerEntityService;

    @Autowired
    private DTOMapper mapper;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet p = mapper.toPetEntity(petDTO);
        if (petDTO.getOwnerId() != 0) {
            com.udacity.jdnd.course3.critter.user.Customer owner = customerEntityService.findById(petDTO.getOwnerId());
            if (owner == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found");
            }
            p.setOwner(owner);
            owner.getPets().add(p);
        }
        Pet saved = petEntityService.save(p);
        return mapper.toPetDTO(saved);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet saved = petEntityService.findById(petId);
        if (saved == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
        return mapper.toPetDTO(saved);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petEntityService.findAll();
        List<PetDTO> res = new ArrayList<>();
        for (Pet pet : pets) res.add(mapper.toPetDTO(pet));
        return res;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petEntityService.findByOwnerId(ownerId);
        List<PetDTO> res = new ArrayList<>();
        for (Pet pet : pets) res.add(mapper.toPetDTO(pet));
        return res;
    }
}
