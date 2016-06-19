package com.keeperteacher.ktservice.video.sync;

import com.keeperteacher.ktservice.core.exception.KtserviceException;

public class VideoSyncException extends KtserviceException {
    public VideoSyncException(String message) {
        super(message);
    }

    public VideoSyncException(String message, Exception e) {
        super(message, e);
    }
}
