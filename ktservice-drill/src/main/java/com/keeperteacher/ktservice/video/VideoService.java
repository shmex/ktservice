package com.keeperteacher.ktservice.video;

import com.keeperteacher.ktservice.core.service.BaseService;
import com.keeperteacher.ktservice.video.upload.VideoSyncHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class VideoService extends BaseService<Video> {

    private static final Logger LOG = LoggerFactory.getLogger(VideoService.class);
    @Autowired private VideoSyncHandler videoSyncHandler;

    public void synchronizeVideo(Video video, byte[] fileData) throws IOException {
        File file = cacheVideo(video, fileData);
        videoSyncHandler.uploadFileToAws(video, file);
    }

    public File cacheVideo(Video video, byte[] videoData) throws IOException {
        String filename = getCacheNameForVideo(video);
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        fileOutputStream.write(videoData);
        fileOutputStream.close();
        return new File(filename);
    }

    public void deleteCachedVideo(Video video) {
        deleteCachedVideo(new File(getCacheNameForVideo(video)));
    }

    public void deleteCachedVideo(File file) {
        file.delete();
    }

    private String getCacheNameForVideo(Video video) {
        return video.getId() + "-" + video.getName();
    }
}
