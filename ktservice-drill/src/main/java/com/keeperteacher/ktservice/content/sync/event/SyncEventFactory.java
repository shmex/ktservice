package com.keeperteacher.ktservice.content.sync.event;

import com.keeperteacher.ktservice.content.sync.SyncState;
import org.springframework.stereotype.Service;

@Service
public class SyncEventFactory {

    public StateTransitionEvent createStateTransitionEvent(String videoId, SyncState baseState, SyncState targetState) {
        return new StateTransitionEvent(videoId, baseState, targetState);
    }

    public ProgressEvent createProgressEvent(String videoId, double progress) {
        return new ProgressEvent(videoId, progress);
    }

}
