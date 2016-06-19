package com.keeperteacher.ktservice.video;

import com.keeperteacher.ktservice.core.exception.ResourceNotFoundException;
import com.keeperteacher.ktservice.core.service.BaseService;
import com.keeperteacher.ktservice.video.sync.VideoSyncConfigService;
import com.keeperteacher.ktservice.video.sync.VideoSyncEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoService extends BaseService<Video> {

    private static final Logger LOG = LoggerFactory.getLogger(VideoService.class);
    @Autowired private VideoSyncConfigService videoSyncConfigService;
    @Autowired private VideoSyncEngine videoSyncEngine;

    @Override
    @Transactional(readOnly = true)
    public List<Video> list() {
        List<Video> videos = super.list();
        videos.stream().forEach(video -> video.setUrl(videoSyncConfigService.getUrlForVideo(video)));
        return videos;
    }

    @Override
    @Transactional(readOnly = true)
    public Video read(String id) throws ResourceNotFoundException {
        Video entity = super.read(id);
        entity.setUrl(videoSyncConfigService.getUrlForVideo(entity));
        return entity;
    }

    @Override
    @Transactional
    public Video update(String id, Video entity) {
        entity = super.update(id, entity);
        entity.setUrl(videoSyncConfigService.getUrlForVideo(entity));
        return entity;
    }

    @Override
    @Transactional
    public Video create(Video entity) {
        entity = super.create(entity);
        entity.setUrl(videoSyncConfigService.getUrlForVideo(entity));
        return entity;
    }

    @Override
    @Transactional
    public Video delete(String id) {
        Video video = super.delete(id);
        videoSyncEngine.delete(video);
        return video;
    }
}
