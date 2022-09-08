package com.vinnissaum.mycontactsspring.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vinnissaum.mycontactsspring.entities.Contact;
import com.vinnissaum.mycontactsspring.repositories.ContactRepository;
import com.vinnissaum.mycontactsspring.services.errors.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactService {
    private final ContactRepository repository;

    @Transactional
    public List<Contact> findAll(Sort direction) {
        return repository.findAll(direction);
    }

    @Transactional
    public Contact findById(UUID id) {
        Optional<Contact> obj = repository.findById(id);
        return obj.orElseThrow(
            () -> new ResourceNotFoundException("Contact not found: " + id));
    }
}
