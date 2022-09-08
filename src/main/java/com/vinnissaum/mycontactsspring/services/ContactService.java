package com.vinnissaum.mycontactsspring.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vinnissaum.mycontactsspring.entities.Contact;
import com.vinnissaum.mycontactsspring.repositories.ContactRepository;
import com.vinnissaum.mycontactsspring.services.errors.DatabaseException;
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

    @Transactional
    public Contact create(Contact entity) {
        Contact contact = repository.save(entity);

        return repository.save(contact);
    }

    @Transactional
    public Contact update(UUID id, Contact entity) {
        try {
            Contact obj = repository.getReferenceById(id);
            obj.setName(entity.getName());
            obj.setEmail(entity.getEmail());
            obj.setPhone(entity.getPhone());
            return repository.save(obj);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Contact not found: " + id);
        }
    }

    public void delete(UUID id) {
        try {
            repository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Contact not found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation: " + e.getMessage());
        }
    }
}
