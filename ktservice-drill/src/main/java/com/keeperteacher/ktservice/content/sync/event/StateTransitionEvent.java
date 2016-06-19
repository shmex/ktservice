package com.keeperteacher.ktservice.content.sync.event;

import com.keeperteacher.ktservice.content.sync.SyncState;

public class StateTransitionEvent extends SyncEvent {

    private SyncState baseState;
    private SyncState targetState;

    public StateTransitionEvent(String videoId, SyncState baseState, SyncState targetState) {
        super(SyncEventType.STATE_TRANSITION_EVENT, videoId);
        this.baseState = baseState;
        this.targetState = targetState;
    }

    public SyncState getBaseState() {
        return baseState;
    }

    public void setBaseState(SyncState baseState) {
        this.baseState = baseState;
    }

    public SyncState getTargetState() {
        return targetState;
    }

    public void setTargetState(SyncState targetState) {
        this.targetState = targetState;
    }
}
