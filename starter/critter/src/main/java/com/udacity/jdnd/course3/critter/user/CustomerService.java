package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public CustomerDTO saveCustomer(CustomerDTO customerDTO){
        Customer c = new Customer();
        c.setName(customerDTO.getName());
        c.setPhoneNumber(customerDTO.getPhoneNumber());
        c.setNotes(customerDTO.getNotes());
        Customer saved = customerRepository.save(c);
        CustomerDTO out = new CustomerDTO();
        out.setId(saved.getId());
        out.setName(saved.getName());
        out.setPhoneNumber(saved.getPhoneNumber());
        out.setNotes(saved.getNotes());
        out.setPetIds(new ArrayList<>());
        return out;
    }

    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> result = new ArrayList<>();
        for (Customer c : customers) {
            CustomerDTO dto = new CustomerDTO();
            dto.setId(c.getId());
            dto.setName(c.getName());
            dto.setPhoneNumber(c.getPhoneNumber());
            dto.setNotes(c.getNotes());
            List<Long> petIds = new ArrayList<>();
            if (c.getPets() != null) {
                for (Pet p : c.getPets()) petIds.add(p.getId());
            }
            dto.setPetIds(petIds);
            result.add(dto);
        }
        return result;
    }

    public CustomerDTO getOwnerByPet(long petId){
        Optional<Pet> p = petRepository.findById(petId);
        if (p.isEmpty() || p.get().getOwner() == null) return null;
        Customer c = p.get().getOwner();
        CustomerDTO dto = new CustomerDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setPhoneNumber(c.getPhoneNumber());
        dto.setNotes(c.getNotes());
        List<Long> petIds = new ArrayList<>();
        if (c.getPets() != null) for (Pet pet : c.getPets()) petIds.add(pet.getId());
        dto.setPetIds(petIds);
        return dto;
    }
}
