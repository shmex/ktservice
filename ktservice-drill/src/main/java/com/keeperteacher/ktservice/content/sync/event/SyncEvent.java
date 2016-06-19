package com.keeperteacher.ktservice.content.sync.event;

public abstract class SyncEvent {

    private final SyncEventType syncEventType;
    private final String videoId;

    public SyncEvent(SyncEventType syncEventType, String videoId) {
        this.syncEventType = syncEventType;
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    public SyncEventType getSyncEventType() {
        return syncEventType;
    }
}
