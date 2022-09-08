package com.vinnissaum.mycontactsspring.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinnissaum.mycontactsspring.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, UUID>{}
