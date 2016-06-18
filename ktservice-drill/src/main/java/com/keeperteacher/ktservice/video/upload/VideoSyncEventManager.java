package com.keeperteacher.ktservice.video.upload;

import com.keeperteacher.ktservice.content.sync.SyncStage;
import com.keeperteacher.ktservice.content.sync.SyncStageFactory;
import com.keeperteacher.ktservice.content.sync.UnsupportedSyncStageException;
import com.keeperteacher.ktservice.video.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VideoSyncEventManager {

    private static final Logger LOG = LoggerFactory.getLogger(VideoSyncEventManager.class);
    private final Map<String, SyncStage> syncMap = new HashMap<>();
    @Autowired private SyncStageFactory syncStageFactory;

    public void setStageForVideo(Video video) {
        try {
            syncMap.put(video.getId(), syncStageFactory.getSyncStageForState(video.getSyncState()));
            LOG.info(String.format("Video: %s is in state: " + video.getSyncState()));
        } catch (UnsupportedSyncStageException e) {
            LOG.error("Could not register tracking for video synchronization! " + e);
        }
    }

    public void updateProgressForVideo(Video video, double progress) {
        SyncStage stage = syncMap.get(video.getId());
        if(stage == null) return;
        stage.setProgress(progress);
        LOG.info(String.format("Video: %s progress: %,.2f%%", video.getId(), progress));
    }

    public void stopTrackingForVideo(Video video) {
        syncMap.remove(video.getId());
        LOG.info(String.format("Video: %s is no longer being tracked. Finishing state: %s", video.getId(), video.getSyncState()));
    }
}
