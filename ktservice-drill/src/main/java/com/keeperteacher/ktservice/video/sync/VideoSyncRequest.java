package com.keeperteacher.ktservice.video.sync;

import com.keeperteacher.ktservice.video.Video;

import java.io.File;

public class VideoSyncRequest {

    private Video video;
    private File file;

    public VideoSyncRequest(Video video, File file) {
        this.video = video;
        this.file = file;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
