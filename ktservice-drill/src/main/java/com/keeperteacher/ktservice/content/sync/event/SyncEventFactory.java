package com.keeperteacher.ktservice.content.sync.event;

import com.keeperteacher.ktservice.content.sync.SyncState;
import com.keeperteacher.ktservice.video.Video;
import org.springframework.stereotype.Service;

@Service
public class SyncEventFactory {

    public StateTransitionEvent createStateTransitionEvent(String videoId, SyncState baseState, SyncState targetState) {
        return new StateTransitionEvent(videoId, baseState, targetState);
    }

    public ProgressEvent createProgressEvent(Video video, double progress) {
        return new ProgressEvent(video.getId(), progress, video.getSyncState());
    }

}
