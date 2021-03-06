package com.rest.web.services.imagemanipulator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image extends BaseEntity {

    private String name;

    private String type;

    @Lob
    private byte[] content;
}
