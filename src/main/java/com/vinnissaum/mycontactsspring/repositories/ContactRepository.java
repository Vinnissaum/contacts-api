package com.vinnissaum.mycontactsspring.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vinnissaum.mycontactsspring.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID>{}
