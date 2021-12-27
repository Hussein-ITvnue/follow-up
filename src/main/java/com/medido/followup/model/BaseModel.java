package com.medido.followup.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class BaseModel {

    @Column(name = "created", insertable = false, updatable = false)
    private String created;

    @Column(name = "updated", insertable = false, updatable = false)
    private String updated;

    @Column(name = "deleted", insertable = false, updatable = false)
    private Boolean deleted;
}
