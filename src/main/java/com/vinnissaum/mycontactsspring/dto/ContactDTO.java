package com.vinnissaum.mycontactsspring.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeId;
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

    @JsonTypeId
    @JsonProperty("category_id")
    private UUID categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    public ContactDTO(Contact entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
        this.categoryName = entity.getCategory() == null ? null : entity.getCategory().getName();
    }
}
