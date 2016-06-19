package com.keeperteacher.ktservice.video.sync;

public class VideoSyncUploadException extends VideoSyncException {
    public VideoSyncUploadException(String message) {
        super(message);
    }

    public VideoSyncUploadException(String message, Exception e) {
        super(message, e);
    }
}
