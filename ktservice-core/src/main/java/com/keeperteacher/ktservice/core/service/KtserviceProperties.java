package com.keeperteacher.ktservice.core.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

@Service
public class KtserviceProperties {

    private static final String PROPERTIES_FILE_PATH = "/ktservice.properties";
    private Properties properties;
    private Object propertiesLock = new Object();

    public static final String VIDEO_SYNC_EVENT_THROTTLE_FREQUENCY = "video.sync.event.throttle.frequency";
    public static final String VIDEO_SYNC_BUCKET_NAME = "video.sync.bucket.name";
    public static final String VIDEO_SYNC_BASE_URL = "video.sync.base.url";
    public static final String VIDEO_SYNC_ELASTIC_TRANSCODER_PIPELINE_ID = "video.sync.elastic.transcoder.pipeline.id";
    public static final String VIDEO_SYNC_ELASTIC_TRANSCODER_WEBM_PRESET_ID = "video.sync.elastic.transcoder.webm.preset.id";
    public static final String VIDEO_SYNC_ELASTIC_TRANSCODER_HLS_400K_PRESET_ID = "video.sync.elastic.transcoder.hls.400k.preset.id";
    public static final String VIDEO_SYNC_ELASTIC_TRANSCODER_HLS_1M_PRESET_ID = "video.sync.elastic.transcoder.hls.1M.preset.id";
    public static final String VIDEO_SYNC_ELASTIC_TRANSCODER_HLS_2M_PRESET_ID = "video.sync.elastic.transcoder.hls.2M.preset.id";
    public static final String VIDEO_SYNC_ELASTIC_TRANSCODER_HLS_SEGMENT_DURATION = "video.sync.elastic.transcoder.hls.segment.duration";

    public KtserviceProperties() throws IOException {
        refreshProperties();
    }

    public void refreshProperties() throws IOException {
        ClassPathResource resource = new ClassPathResource(PROPERTIES_FILE_PATH);
        synchronized (propertiesLock) {
            properties = PropertiesLoaderUtils.loadProperties(resource);
        }
    }

    public String getProperty(String key) {
        synchronized (propertiesLock) {
            return properties.getProperty(key);
        }
    }

    public String getProperty(String key, String defaultValue) {
        synchronized (propertiesLock) {
            return properties.getProperty(key, defaultValue);
        }
    }
}
