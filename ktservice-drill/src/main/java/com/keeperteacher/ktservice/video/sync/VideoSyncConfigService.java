package com.keeperteacher.ktservice.video.sync;

import com.keeperteacher.ktservice.core.service.KtserviceProperties;
import com.keeperteacher.ktservice.video.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoSyncConfigService {

    @Autowired private KtserviceProperties ktserviceProperties;

    public String getBucketName() {
        return ktserviceProperties.getProperty(KtserviceProperties.VIDEO_SYNC_BUCKET_NAME);
    }

    public String getBaseUrl() {
        return ktserviceProperties.getProperty(KtserviceProperties.VIDEO_SYNC_BASE_URL);
    }

    public String getUrlForVideo(Video video) {
        return String.format("%s/%s/%s", getBaseUrl(), getBucketName(), video.getId());
    }

}
