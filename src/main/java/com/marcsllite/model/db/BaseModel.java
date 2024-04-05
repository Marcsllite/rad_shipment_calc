package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@MappedSuperclass
public abstract class BaseModel implements Serializable {
    private static final long serialVersionUID = -7070688536494118785L;

    @Version
    @ColumnDefault("0")
    private long version;
    @Transient
    private Conversions.SIPrefix basePrefix;

    public Conversions.SIPrefix getBasePrefix() {
        return basePrefix;
    }

    public void setBasePrefix(Conversions.SIPrefix basePrefix) {
        this.basePrefix = basePrefix;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
