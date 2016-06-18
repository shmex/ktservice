package com.keeperteacher.ktservice.video;

import com.keeperteacher.ktservice.aws.s3.AwsS3Service;
import com.keeperteacher.ktservice.aws.s3.S3UploadProgress;
import com.keeperteacher.ktservice.content.SyncState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class VideoUploadHandler {

    private static final Logger LOG = LoggerFactory.getLogger(VideoUploadHandler.class);
    @Autowired private VideoService videoService;
    @Autowired private AwsS3Service awsS3Service;

    @Async
    public void uploadFileToAws(Video video, File file) {
        final S3UploadProgress progress = new S3UploadProgress(file.length());
        awsS3Service.uploadFile("ktservice-test", video.getId(), file, progressEvent -> {
            switch(progressEvent.getEventType()) {
                case TRANSFER_STARTED_EVENT:
                    LOG.info("Synchronization started.");
                    video.setSyncState(SyncState.SYNCHRONIZING);
                    videoService.update(video.getId(), video);
                    break;
                case REQUEST_BYTE_TRANSFER_EVENT:
                    progress.addBytesTransferred(progressEvent.getBytesTransferred());
                    LOG.info(String.format("Progress: %1$,.2f%%", progress.getPercentComplete()));
                    break;
                case TRANSFER_COMPLETED_EVENT:
                    LOG.info("Synchronization finished successfully.");
                    video.setSyncState(SyncState.COMPLETED);
                    videoService.update(video.getId(), video);
                    file.delete();
                    break;
                case TRANSFER_FAILED_EVENT:
                    LOG.info("Synchronization failed.");
                    video.setSyncState(SyncState.FAILED);
                    videoService.update(video.getId(), video);
                    file.delete();
                    break;
                case TRANSFER_CANCELED_EVENT:
                    LOG.info("Synchronization cancelled.");
                    video.setSyncState(SyncState.CANCELED);
                    videoService.update(video.getId(), video);
                    file.delete();
                    break;
            }
        });
    }
}
