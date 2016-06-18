package com.keeperteacher.ktservice.content.sync;

import com.keeperteacher.ktservice.core.exception.KtserviceException;

public class UnsupportedSyncStageException extends KtserviceException {
    public UnsupportedSyncStageException(String message) {
        super(message);
    }

    public UnsupportedSyncStageException(String message, Exception e) {
        super(message, e);
    }
}
