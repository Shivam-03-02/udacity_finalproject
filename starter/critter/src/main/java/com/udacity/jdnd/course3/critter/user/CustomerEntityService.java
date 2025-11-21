package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerEntityService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer findById(Long id){
        Optional<Customer> o = customerRepository.findById(id);
        return o.orElse(null);
    }

    public Customer findOwnerByPetId(Long petId){
        Optional<Pet> p = petRepository.findById(petId);
        if (p.isEmpty()) return null;
        return p.get().getOwner();
    }
}
