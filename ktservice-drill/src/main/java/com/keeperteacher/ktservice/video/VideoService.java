package com.keeperteacher.ktservice.video;

import com.keeperteacher.ktservice.core.exception.ResourceNotFoundException;
import com.keeperteacher.ktservice.core.service.BaseService;
import com.keeperteacher.ktservice.video.sync.VideoSyncHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class VideoService extends BaseService<Video> {

    private static final Logger LOG = LoggerFactory.getLogger(VideoService.class);
    @Autowired private VideoSyncHandler videoSyncHandler;

    @Override
    @Transactional(readOnly = true)
    public List<Video> list() {
        List<Video> videos = super.list();
        videos.stream().forEach(video -> video.setUrl(videoSyncHandler.getUrlForVideo(video)));
        return videos;
    }

    @Override
    @Transactional(readOnly = true)
    public Video read(String id) throws ResourceNotFoundException {
        Video entity = super.read(id);
        entity.setUrl(videoSyncHandler.getUrlForVideo(entity));
        return entity;
    }

    @Override
    @Transactional
    public Video update(String id, Video entity) {
        entity = super.update(id, entity);
        entity.setUrl(videoSyncHandler.getUrlForVideo(entity));
        return entity;
    }

    @Override
    @Transactional
    public Video create(Video entity) {
        entity = super.create(entity);
        entity.setUrl(videoSyncHandler.getUrlForVideo(entity));
        return entity;
    }

    @Override
    @Transactional
    public Video delete(String id) {
        Video video = super.delete(id);
        videoSyncHandler.deleteVideoOnAws(video);
        return video;
    }

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
