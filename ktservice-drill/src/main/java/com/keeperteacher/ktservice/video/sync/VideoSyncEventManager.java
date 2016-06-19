package com.keeperteacher.ktservice.video.sync;

import com.keeperteacher.ktservice.content.sync.event.ProgressEvent;
import com.keeperteacher.ktservice.content.sync.event.StateTransitionEvent;
import com.keeperteacher.ktservice.content.sync.event.SyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VideoSyncEventManager {

    private static final Logger LOG = LoggerFactory.getLogger(VideoSyncEventManager.class);

    public void publish(SyncEvent syncEvent) {
        // todo actually send these messages somewhere

        switch (syncEvent.getSyncEventType()) {
            case STATE_TRANSITION_EVENT:
                StateTransitionEvent transitionEvent = (StateTransitionEvent) syncEvent;
                LOG.info(String.format("Video: %s transitioned to state: %s", transitionEvent.getVideoId(), transitionEvent.getTargetState()));
                break;
            case PROGRESS_EVENT:
                ProgressEvent progressEvent = (ProgressEvent) syncEvent;
                LOG.info(String.format("Video: %s progress: %,.2f%%", progressEvent.getVideoId(), progressEvent.getProgress()));
                break;
        }
    }

    public void subscribe(String videoId) {
    }
}
