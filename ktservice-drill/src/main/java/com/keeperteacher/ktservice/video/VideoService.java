package com.keeperteacher.ktservice.video;

import com.keeperteacher.ktservice.core.service.BaseService;
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
    @Autowired private VideoUploadHandler videoUploadHandler;

    public void synchronizeVideo(Video video, byte[] fileData) throws IOException {
        File file = cacheFile(video.getName(), fileData);
        videoUploadHandler.uploadFileToAws(video, file);
    }

    private File cacheFile(String filename, byte[] fileData) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        fileOutputStream.write(fileData);
        fileOutputStream.close();
        return new File(filename);
    }
}
