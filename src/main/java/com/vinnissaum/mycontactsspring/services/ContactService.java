package com.vinnissaum.mycontactsspring.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vinnissaum.mycontactsspring.dto.ContactDTO;
import com.vinnissaum.mycontactsspring.entities.Contact;
import com.vinnissaum.mycontactsspring.repositories.ContactRepository;
import com.vinnissaum.mycontactsspring.services.errors.DatabaseException;
import com.vinnissaum.mycontactsspring.services.errors.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactService {
    private final ContactRepository repository;

    private static final String CONTACT_NOT_FOUND = "Contact not found: ";

    @Transactional
    public List<ContactDTO> findAll(Sort direction) {
        List<Contact> list = repository.findAll(direction);

        return list.stream().map(ContactDTO::new).toList();
    }

    @Transactional
    public ContactDTO findById(UUID id) {
        Optional<Contact> obj = repository.findById(id);
        Contact entity = obj.orElseThrow(
            () -> new ResourceNotFoundException(CONTACT_NOT_FOUND + id));

        return new ContactDTO(entity);
    }

    @Transactional
    public ContactDTO create(ContactDTO dto) {
        Contact entity = new Contact();
        emailExists(dto.getEmail());
        toEntity(entity, dto);
        entity = repository.save(entity);

        return new ContactDTO(entity);
    }

    @Transactional
    public ContactDTO update(UUID id, ContactDTO dto) {
        try {
            Contact entity = repository.getReferenceById(id);
            emailExists(dto.getEmail());
            toEntity(entity, dto);
            entity = repository.save(entity);
            return new ContactDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(CONTACT_NOT_FOUND + id);
        }
    }

    public void delete(UUID id) {
        try {
            repository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(CONTACT_NOT_FOUND + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation: " + e.getMessage());
        }
    }

    public void emailExists(String email) {
        Contact emailExists = repository.findByEmail(email);

        if (emailExists != null) {
            throw new DatabaseException("This email has already been taken");
        }
    }

    public void toEntity(Contact entity, ContactDTO dto) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
    }
}
