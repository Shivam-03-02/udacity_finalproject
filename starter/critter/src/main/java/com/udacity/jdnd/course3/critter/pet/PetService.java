package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public PetDTO savePet(PetDTO petDTO){
        Pet p = new Pet();
        p.setName(petDTO.getName());
        p.setType(petDTO.getType());
        p.setBirthDate(petDTO.getBirthDate());
        p.setNotes(petDTO.getNotes());
        if (petDTO.getOwnerId() != 0) {
            Optional<Customer> c = customerRepository.findById(petDTO.getOwnerId());
            c.ifPresent(p::setOwner);
        }
        Pet saved = petRepository.save(p);
        if (saved.getOwner() != null){
            Customer owner = saved.getOwner();
            if (owner.getPets() == null) owner.setPets(new ArrayList<>());
            boolean contains = owner.getPets().stream().anyMatch(pet -> pet.getId().equals(saved.getId()));
            if (!contains) {
                owner.getPets().add(saved);
                customerRepository.save(owner);
            }
        }
        PetDTO out = new PetDTO();
        out.setId(saved.getId());
        out.setName(saved.getName());
        out.setType(saved.getType());
        out.setBirthDate(saved.getBirthDate());
        out.setNotes(saved.getNotes());
        out.setOwnerId(saved.getOwner() != null ? saved.getOwner().getId() : 0);
        return out;
    }

    public PetDTO getPet(long petId){
        Optional<Pet> p = petRepository.findById(petId);
        if (p.isEmpty()) return null;
        Pet pet = p.get();
        PetDTO dto = new PetDTO();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setType(pet.getType());
        dto.setBirthDate(pet.getBirthDate());
        dto.setNotes(pet.getNotes());
        dto.setOwnerId(pet.getOwner() != null ? pet.getOwner().getId() : 0);
        return dto;
    }

    public List<PetDTO> getPets(){
        List<Pet> pets = petRepository.findAll();
        List<PetDTO> res = new ArrayList<>();
        for (Pet pet : pets){
            PetDTO dto = new PetDTO();
            dto.setId(pet.getId());
            dto.setName(pet.getName());
            dto.setType(pet.getType());
            dto.setBirthDate(pet.getBirthDate());
            dto.setNotes(pet.getNotes());
            dto.setOwnerId(pet.getOwner() != null ? pet.getOwner().getId() : 0);
            res.add(dto);
        }
        return res;
    }

    public List<PetDTO> getPetsByOwner(long ownerId){
        List<Pet> pets = petRepository.findByOwnerId(ownerId);
        List<PetDTO> res = new ArrayList<>();
        for (Pet pet : pets){
            PetDTO dto = new PetDTO();
            dto.setId(pet.getId());
            dto.setName(pet.getName());
            dto.setType(pet.getType());
            dto.setBirthDate(pet.getBirthDate());
            dto.setNotes(pet.getNotes());
            dto.setOwnerId(pet.getOwner() != null ? pet.getOwner().getId() : ownerId);
            res.add(dto);
        }
        return res;
    }
}
