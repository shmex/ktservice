package com.keeperteacher.ktservice.content;

import com.keeperteacher.ktservice.content.sync.SyncState;
import com.keeperteacher.ktservice.core.model.PersistedObject;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class Content extends PersistedObject {

    @Column(name = "name")
    private String name;

    @Transient
    private String url;

    @Column(name = "syncState")
    private SyncState syncState;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SyncState getSyncState() {
        return syncState;
    }

    public void setSyncState(SyncState syncState) {
        this.syncState = syncState;
    }
}
