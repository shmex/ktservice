package com.keeperteacher.ktservice.content.sync;

import org.springframework.stereotype.Service;

@Service
public class SyncStageFactory {

    public SyncStage getSyncStageForState(SyncState syncState) throws UnsupportedSyncStageException {
        switch (syncState) {
            case UPLOADING: return new UploadingSyncStage();
            case WAITING:
            case TRANSCODING:
            case COMPLETED:
            case FAILED:
            case CANCELED:
        }

        throw new UnsupportedSyncStageException("Unsupported SyncState for SyncState: " + syncState);
    }
}
