package com.keeperteacher.ktservice.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Repository
@MappedSuperclass
@JsonPropertyOrder({"self", "id", "created", "modified"})
public class PersistedObject {

    @Transient
    private String self;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified;

    @Column(name = "deleted")
    @JsonIgnore
    private boolean deleted;

    public PersistedObject() {
        this.created = new Date();
        this.modified = (Date) created.clone();
        this.deleted = false;
    }

    public String getSelf() {
        return StringUtils.isEmpty(getId()) ? null : "/api/" + getClass().getSimpleName().toLowerCase() + "s/" + getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
