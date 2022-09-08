package com.vinnissaum.mycontactsspring.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinnissaum.mycontactsspring.entities.Contact;
import com.vinnissaum.mycontactsspring.services.ContactService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/contacts")
@AllArgsConstructor
public class ContactController {
    private final ContactService service;

    @GetMapping
    public ResponseEntity<List<Contact>> index(
        @RequestParam(value = "order", defaultValue = "ASC") String order) {
        List<Contact> contacts = service.findAll(Sort.by(Sort.Direction
            .valueOf(order.toUpperCase()), "name"));

        return ResponseEntity.ok(contacts);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Contact> show(@PathVariable UUID id) {
        Contact contact = service.findById(id);

        return ResponseEntity.ok(contact);
    }
}
