package com.keeperteacher.ktservice.content.sync.event;

import com.keeperteacher.ktservice.content.sync.SyncState;

public class ProgressEvent extends SyncEvent {

    private double progress;
    private SyncState syncState;

    public ProgressEvent(String videoId, double progress, SyncState syncState) {
        super(SyncEventType.PROGRESS_EVENT, videoId);
        this.progress = progress;
        this.syncState = syncState;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public SyncState getSyncState() {
        return syncState;
    }

    public void setSyncState(SyncState syncState) {
        this.syncState = syncState;
    }
}
