package com.vinnissaum.mycontactsspring.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vinnissaum.mycontactsspring.dto.ContactDTO;
import com.vinnissaum.mycontactsspring.services.ContactService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/contacts")
@AllArgsConstructor
public class ContactController {
    private final ContactService service;

    @GetMapping
    public ResponseEntity<List<ContactDTO>> index(
        @RequestParam(value = "order", defaultValue = "ASC") String order) {
        List<ContactDTO> contacts = service.findAll(Sort.by(Sort.Direction
            .valueOf(order.toUpperCase()), "name"));

        return ResponseEntity.ok(contacts);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactDTO> show(@PathVariable UUID id) {
        ContactDTO contact = service.findById(id);

        return ResponseEntity.ok(contact);
    }

    @PostMapping
    public ResponseEntity<ContactDTO> create(@RequestBody ContactDTO entity) {
        ContactDTO contact = service.create(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entity.getId()).toUri();

        return ResponseEntity.created(uri).body(contact);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ContactDTO> create(@RequestBody ContactDTO entity,
    @PathVariable UUID id) {
        ContactDTO contact = service.update(id, entity);

        return ResponseEntity.ok(contact);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ContactDTO> delete(@PathVariable UUID id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
