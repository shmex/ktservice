package com.keeperteacher.ktservice.video.sync;

import com.keeperteacher.ktservice.aws.s3.AwsS3Service;
import com.keeperteacher.ktservice.aws.s3.S3UploadProgress;
import com.keeperteacher.ktservice.content.sync.SyncState;
import com.keeperteacher.ktservice.content.sync.event.SyncEventFactory;
import com.keeperteacher.ktservice.core.service.KtserviceProperties;
import com.keeperteacher.ktservice.throttle.Throttle;
import com.keeperteacher.ktservice.video.Video;
import com.keeperteacher.ktservice.video.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Lazy
public class VideoSyncHandler {

    private static final Logger LOG = LoggerFactory.getLogger(VideoSyncHandler.class);
    @Autowired private KtserviceProperties ktserviceProperties;
    @Autowired private VideoService videoService;
    @Autowired private AwsS3Service awsS3Service;
    @Autowired private VideoSyncEventManager videoSyncEventManager;
    @Autowired private SyncEventFactory syncEventFactory;

    @Async
    public void uploadFileToAws(Video video, File file) {
        int throttleFrequency = Integer.parseInt(ktserviceProperties.getProperty(KtserviceProperties.VIDEO_SYNC_EVENT_THROTTLE_FREQUENCY));
        final Throttle throttle = new Throttle(throttleFrequency); // 1 times per second
        final S3UploadProgress progress = new S3UploadProgress(file.length());
        awsS3Service.uploadFile(getBucketName(), video.getId(), file, progressEvent -> {

            SyncState originalState = video.getSyncState();

            switch(progressEvent.getEventType()) {
                case TRANSFER_STARTED_EVENT:
                    video.setSyncState(SyncState.UPLOADING);
                    videoService.update(video.getId(), video);
                    break;
                case REQUEST_BYTE_TRANSFER_EVENT:
                    progress.addBytesTransferred(progressEvent.getBytesTransferred());
                    throttle.throttledRun(() ->
                        videoSyncEventManager.publish(syncEventFactory.createProgressEvent(video.getId(), progress.getPercentComplete()))
                    );
                    break;
                case TRANSFER_COMPLETED_EVENT:
                    video.setSyncState(SyncState.COMPLETED);
                    updateVideoAndDeleteCachedFile(video, file);
                    break;
                case TRANSFER_FAILED_EVENT:
                    video.setSyncState(SyncState.FAILED);
                    updateVideoAndDeleteCachedFile(video, file);
                    break;
                case TRANSFER_CANCELED_EVENT:
                    video.setSyncState(SyncState.CANCELED);
                    updateVideoAndDeleteCachedFile(video, file);
                    break;
            }

            if(originalState != video.getSyncState()) {
                videoSyncEventManager.publish(syncEventFactory.createStateTransitionEvent(video.getId(), originalState, video.getSyncState()));
            }
        });
    }

    public void deleteVideoOnAws(Video video) {
        awsS3Service.deleteFile(getBucketName(), video.getId());
    }

    public String getBucketName() {
        return ktserviceProperties.getProperty(KtserviceProperties.VIDEO_SYNC_BUCKET_NAME);
    }

    public String getBaseUrl() {
        return ktserviceProperties.getProperty(KtserviceProperties.VIDEO_SYNC_BASE_URL);
    }

    public String getUrlForVideo(Video video) {
        return String.format("%s/%s/%s", getBaseUrl(), getBucketName(), video.getId());
    }

    private void updateVideoAndDeleteCachedFile(Video video, File file) {
        videoService.update(video.getId(), video);
        videoService.deleteCachedVideo(file);
    }
}
