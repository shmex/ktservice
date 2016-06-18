package com.keeperteacher.ktservice.video;

import com.keeperteacher.ktservice.content.SyncState;
import com.keeperteacher.ktservice.core.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/videos", produces = "application/json")
public class VideoApi {

    private static final Logger LOG = LoggerFactory.getLogger(VideoApi.class);
    @Autowired
    private VideoService videoService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "multipart/*")
    public Video test(@RequestParam("file") MultipartFile file) throws IOException {
        LOG.info("received file: " + file.getOriginalFilename());
        Video video = new Video();
        video.setName(file.getOriginalFilename());
        video.setSyncState(SyncState.WAITING);

        video = videoService.create(video);
        videoService.synchronizeVideo(video, file.getBytes());

        return video;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Video> list() {
        return videoService.list();
    }

    @RequestMapping(value = "{videoId}", method = RequestMethod.GET)
    public Video read(@PathVariable String videoId) throws ResourceNotFoundException {
        return videoService.read(videoId);
    }

    @RequestMapping(value = "{videoId}", method = RequestMethod.PUT)
    public Video update(@PathVariable String videoId, Video video) {
        return videoService.update(videoId, video);
    }

    @RequestMapping(value = "{videoId}", method = RequestMethod.DELETE)
    public Video delete(@PathVariable String videoId) {
        return videoService.delete(videoId);
    }
}
