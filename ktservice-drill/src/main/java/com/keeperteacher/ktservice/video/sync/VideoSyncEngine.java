package com.keeperteacher.ktservice.video.sync;

import com.keeperteacher.ktservice.aws.elastictranscoder.AwsElasticTranscoderService;
import com.keeperteacher.ktservice.content.sync.SyncState;
import com.keeperteacher.ktservice.content.sync.event.SyncEventFactory;
import com.keeperteacher.ktservice.core.service.KtserviceProperties;
import com.keeperteacher.ktservice.video.Video;
import com.keeperteacher.ktservice.video.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
@Lazy
public class VideoSyncEngine {

    private static final Logger LOG = LoggerFactory.getLogger(VideoSyncEngine.class);
    @Autowired private VideoService videoService;
    @Autowired private VideoSyncUploader videoSyncUploader;
    @Autowired private VideoSyncTranscoder videoSyncTranscoder;
    @Autowired private VideoSyncEventManager videoSyncEventManager;
    @Autowired private SyncEventFactory syncEventFactory;

    @Async
    public void synchronize(Video video, byte[] data) {
        File file = null;
        try {
            file = cacheVideo(video, data);
            upload(video, file);
            transcode(video);
            performStateTransition(video, SyncState.COMPLETED);
        } catch(Exception e) {
            LOG.error("Video Sync Failed! " + e.toString());
            performStateTransition(video, SyncState.FAILED);
        } finally {
            if(file != null) {
                file.delete();
            }
        }
    }

    @Async
    public void delete(Video video) {
        videoSyncUploader.deleteVideoOnAws(video);
    }

    private File cacheVideo(Video video, byte[] videoData) throws IOException {
        String filename = getCacheNameForVideo(video);
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        fileOutputStream.write(videoData);
        fileOutputStream.close();
        return new File(filename);
    }

    private String getCacheNameForVideo(Video video) {
        return video.getId() + "-" + video.getName();
    }

    private void upload(Video video, File file) throws VideoSyncUploadException {
        performStateTransition(video, SyncState.UPLOADING);
        videoSyncUploader.uploadFileToAws(video, file);
    }

    private void transcode(Video video) {
        performStateTransition(video, SyncState.TRANSCODING);
        videoSyncTranscoder.transcodeToWebm(video);
        videoSyncTranscoder.transcodeToHLS(video);
    }

    private void performStateTransition(Video video, SyncState syncState) {
        SyncState previousState = video.getSyncState();
        video.setSyncState(syncState);
        videoService.update(video.getId(), video);
        videoSyncEventManager.publish(syncEventFactory.createStateTransitionEvent(video.getId(), previousState, video.getSyncState()));
    }
}
