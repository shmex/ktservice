package com.keeperteacher.ktservice.content.sync.event;

public class ProgressEvent extends SyncEvent {

    private double progress;

    public ProgressEvent(String videoId, double progress) {
        super(SyncEventType.PROGRESS_EVENT, videoId);
        this.progress = progress;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
