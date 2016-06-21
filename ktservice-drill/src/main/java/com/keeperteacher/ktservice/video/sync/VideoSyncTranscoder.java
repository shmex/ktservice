package com.keeperteacher.ktservice.video.sync;

import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobPlaylist;
import com.amazonaws.services.elastictranscoder.model.JobInput;
import com.keeperteacher.ktservice.aws.elastictranscoder.AwsElasticTranscoderService;
import com.keeperteacher.ktservice.core.service.KtserviceProperties;
import com.keeperteacher.ktservice.video.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class VideoSyncTranscoder {

    @Autowired private AwsElasticTranscoderService awsElasticTranscoderService;
    @Autowired private KtserviceProperties ktserviceProperties;

    public void transcodeToWebm(Video video) {
        String pipelineId = ktserviceProperties.getProperty(KtserviceProperties.VIDEO_SYNC_ELASTIC_TRANSCODER_PIPELINE_ID);
        String presetId = ktserviceProperties.getProperty(KtserviceProperties.VIDEO_SYNC_ELASTIC_TRANSCODER_WEBM_PRESET_ID);
        String outputKeyPrefix = getOutputKeyPrefixForVideo(video);

        JobInput input = new JobInput().withKey(video.getId());

        CreateJobOutput output = new CreateJobOutput();
        output.setKey(video.getId() + ".webm");
        output.setPresetId(presetId);

        awsElasticTranscoderService.transcode(pipelineId, outputKeyPrefix, input, output);
    }

    public void transcodeToHLS(Video video) {
        String pipelineId = ktserviceProperties.getProperty(KtserviceProperties.VIDEO_SYNC_ELASTIC_TRANSCODER_PIPELINE_ID);
        String presetId400k = ktserviceProperties.getProperty(KtserviceProperties.VIDEO_SYNC_ELASTIC_TRANSCODER_HLS_400K_PRESET_ID);
        String presetId1M = ktserviceProperties.getProperty(KtserviceProperties.VIDEO_SYNC_ELASTIC_TRANSCODER_HLS_1M_PRESET_ID);
        String presetId2M = ktserviceProperties.getProperty(KtserviceProperties.VIDEO_SYNC_ELASTIC_TRANSCODER_HLS_2M_PRESET_ID);
        String outputKeyPrefix = getOutputKeyPrefixForVideo(video);
        String segmentDuration = ktserviceProperties.getProperty(KtserviceProperties.VIDEO_SYNC_ELASTIC_TRANSCODER_HLS_SEGMENT_DURATION);

        JobInput input = new JobInput().withKey(video.getId());
        Set<CreateJobOutput> outputs = new HashSet<>();
        Set<CreateJobPlaylist> playlists = new HashSet<>();

        CreateJobOutput output400k = new CreateJobOutput();
        output400k.setKey("hls_400k_");
        output400k.setPresetId(presetId400k);
        output400k.setSegmentDuration(segmentDuration);
        outputs.add(output400k);

        CreateJobOutput output1M = new CreateJobOutput();
        output1M.setKey("hls_1M_");
        output1M.setPresetId(presetId1M);
        output1M.setSegmentDuration(segmentDuration);
        outputs.add(output1M);

        CreateJobOutput output2M = new CreateJobOutput();
        output2M.setKey("hls_2M_");
        output2M.setPresetId(presetId2M);
        output2M.setSegmentDuration(segmentDuration);
        outputs.add(output2M);

        CreateJobPlaylist playlist = new CreateJobPlaylist();
        playlist.setFormat("HLSv3");
        playlist.setName("index");
        playlist.setOutputKeys(Arrays.asList(output400k.getKey(), output1M.getKey(), output2M.getKey()));
        playlists.add(playlist);

        awsElasticTranscoderService.transcode(pipelineId, outputKeyPrefix, input, outputs, playlists);
    }

    public String getOutputKeyPrefixForVideo(Video video) {
        return video.getId() + "/";
    }
}
