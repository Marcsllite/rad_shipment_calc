package com.marcsllite.model.db;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import java.io.Serializable;

@MappedSuperclass
public abstract class BaseModel implements Serializable {
    private static final long serialVersionUID = -7070688536494118785L;
    @Version private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
