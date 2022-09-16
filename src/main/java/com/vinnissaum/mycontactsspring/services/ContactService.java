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
import com.vinnissaum.mycontactsspring.entities.Category;
import com.vinnissaum.mycontactsspring.entities.Contact;
import com.vinnissaum.mycontactsspring.repositories.CategoryRepository;
import com.vinnissaum.mycontactsspring.repositories.ContactRepository;
import com.vinnissaum.mycontactsspring.services.errors.DatabaseException;
import com.vinnissaum.mycontactsspring.services.errors.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactService {
    private final ContactRepository repository;

    private final CategoryRepository categoryRepository;

    private static final String NOT_FOUND = "Contact not found by id: ";

    @Transactional
    public List<ContactDTO> findAll(Sort direction) {
        List<Contact> list = repository.findAll(direction);

        return list.stream().map(ContactDTO::new).toList();
    }

    @Transactional
    public ContactDTO findById(UUID id) {
        Optional<Contact> obj = repository.findById(id);
        Contact entity = obj.orElseThrow(
            () -> new ResourceNotFoundException(NOT_FOUND + id));

        return new ContactDTO(entity);
    }

    @Transactional
    public ContactDTO create(ContactDTO dto) {
        nameExists(dto.getName());

        Contact entity = new Contact();
        Category category = categoryRepository.getReferenceById(dto.getCategoryId());

        emailExists(dto.getEmail());
        toEntity(entity, dto);
        entity.setCategory(category);
        entity = repository.save(entity);

        return new ContactDTO(entity);
    }

    @Transactional
    public ContactDTO update(UUID id, ContactDTO dto) {
        nameExists(dto.getName());

        try {
            Contact entity = repository.getReferenceById(id);
            emailExists(dto.getEmail());
            toEntity(entity, dto);
            entity = repository.save(entity);
            return new ContactDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(NOT_FOUND + id);
        }
    }

    public void delete(UUID id) {
        try {
            repository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(NOT_FOUND + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation: " + e.getMessage());
        }
    }

    private void nameExists(String name) {
        if (name == null) {
            throw new DatabaseException("Name is required");
        }
    }

    private void emailExists(String email) {
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
