package com.keeperteacher.ktservice.video.sync;

import com.keeperteacher.ktservice.aws.s3.AwsS3Service;
import com.keeperteacher.ktservice.aws.s3.S3UploadProgress;
import com.keeperteacher.ktservice.content.sync.SyncState;
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
    private static final String BUCKET_NAME = "ktservice-test";
    @Autowired private VideoService videoService;
    @Autowired private AwsS3Service awsS3Service;
    @Autowired private VideoSyncEventManager videoSyncEventManager;

    @Async
    public void uploadFileToAws(Video video, File file) {
        final S3UploadProgress progress = new S3UploadProgress(file.length());
        awsS3Service.uploadFile(BUCKET_NAME, video.getId(), file, progressEvent -> {
            switch(progressEvent.getEventType()) {
                case TRANSFER_STARTED_EVENT:
                    video.setSyncState(SyncState.UPLOADING);
                    videoService.update(video.getId(), video);
                    videoSyncEventManager.setStageForVideo(video);
                    break;
                case REQUEST_BYTE_TRANSFER_EVENT:
                    progress.addBytesTransferred(progressEvent.getBytesTransferred());
                    videoSyncEventManager.updateProgressForVideo(video, progress.getPercentComplete());
                    break;
                case TRANSFER_COMPLETED_EVENT:
                    video.setSyncState(SyncState.COMPLETED);
                    updateVideoAndDeleteCachedFile(video, file);
                    videoSyncEventManager.stopTrackingForVideo(video);
                    break;
                case TRANSFER_FAILED_EVENT:
                    video.setSyncState(SyncState.FAILED);
                    updateVideoAndDeleteCachedFile(video, file);
                    videoSyncEventManager.stopTrackingForVideo(video);
                    break;
                case TRANSFER_CANCELED_EVENT:
                    video.setSyncState(SyncState.CANCELED);
                    updateVideoAndDeleteCachedFile(video, file);
                    videoSyncEventManager.stopTrackingForVideo(video);
                    break;
            }
        });
    }

    public void deleteVideoOnAws(Video video) {
        awsS3Service.deleteFile(BUCKET_NAME, video.getId());
    }

    private void updateVideoAndDeleteCachedFile(Video video, File file) {
        videoService.update(video.getId(), video);
        videoService.deleteCachedVideo(file);
    }
}
