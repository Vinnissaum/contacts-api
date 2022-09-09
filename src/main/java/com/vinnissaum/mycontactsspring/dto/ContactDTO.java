package com.vinnissaum.mycontactsspring.dto;

import java.util.UUID;

import com.vinnissaum.mycontactsspring.entities.Contact;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactDTO {

    private UUID id;

    private String name;

    private String email;

    private String phone;

    private String category_name;

    public ContactDTO(Contact entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
        this.category_name = entity.getCategoryName();
    }
}
