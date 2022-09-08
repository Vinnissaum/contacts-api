package com.vinnissaum.mycontactsspring.dto;

import java.util.UUID;

import com.vinnissaum.mycontactsspring.entities.Contact;

import lombok.Data;

@Data
public class ContactDTO {

    private UUID id;

    private String name;

    private String email;

    private String phone;

    public ContactDTO(Contact entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
    }
}
