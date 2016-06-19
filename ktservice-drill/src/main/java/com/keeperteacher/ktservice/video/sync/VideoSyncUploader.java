package com.keeperteacher.ktservice.video.sync;

import com.keeperteacher.ktservice.aws.s3.AwsS3Service;
import com.keeperteacher.ktservice.aws.s3.S3UploadProgress;
import com.keeperteacher.ktservice.content.sync.event.SyncEventFactory;
import com.keeperteacher.ktservice.core.service.KtserviceProperties;
import com.keeperteacher.ktservice.throttle.Throttle;
import com.keeperteacher.ktservice.video.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class VideoSyncUploader {

    private static final Logger LOG = LoggerFactory.getLogger(VideoSyncUploader.class);
    @Autowired private KtserviceProperties ktserviceProperties;
    @Autowired private AwsS3Service awsS3Service;
    @Autowired private VideoSyncConfigService videoSyncConfigService;
    @Autowired private VideoSyncEventManager videoSyncEventManager;
    @Autowired private SyncEventFactory syncEventFactory;

    public void uploadFileToAws(Video video, File file) throws VideoSyncUploadException {
        int throttleFrequency = Integer.parseInt(ktserviceProperties.getProperty(KtserviceProperties.VIDEO_SYNC_EVENT_THROTTLE_FREQUENCY));
        final Throttle throttle = new Throttle(throttleFrequency);
        final S3UploadProgress progress = new S3UploadProgress(file.length());

        // blocks until finished
        try {
            awsS3Service.uploadFile(videoSyncConfigService.getBucketName(), video.getId(), file, progressEvent -> {
                switch(progressEvent.getEventType()) {
                    case REQUEST_BYTE_TRANSFER_EVENT:
                        progress.addBytesTransferred(progressEvent.getBytesTransferred());
                        throttle.throttledRun(() ->
                            videoSyncEventManager.publish(syncEventFactory.createProgressEvent(video, progress.getPercentComplete()))
                        );
                        break;
                    case TRANSFER_FAILED_EVENT:
                    case TRANSFER_CANCELED_EVENT:
                }
            });
        } catch (Exception e) {
            throw new VideoSyncUploadException("Upload failed!", e);
        }

    }

    public void deleteVideoOnAws(Video video) {
        awsS3Service.deleteFile(videoSyncConfigService.getBucketName(), video.getId());
    }
}
