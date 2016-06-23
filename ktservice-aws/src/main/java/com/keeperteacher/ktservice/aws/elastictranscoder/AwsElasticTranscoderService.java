package com.keeperteacher.ktservice.aws.elastictranscoder;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoder;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.model.*;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AwsElasticTranscoderService {

    private AWSCredentials credentials;
    private AmazonElasticTranscoder amazonElasticTranscoder;

    public AwsElasticTranscoderService() {
        Region region = Region.getRegion(Regions.US_WEST_2);
        credentials = new ProfileCredentialsProvider().getCredentials();
        amazonElasticTranscoder = new AmazonElasticTranscoderClient(credentials);
        amazonElasticTranscoder.setRegion(region);
    }

    public void transcode(String pipelineId, String outputKeyPrefix, JobInput input, CreateJobOutput output) {
        CreateJobRequest createJobRequest = new CreateJobRequest();
        createJobRequest.setPipelineId(pipelineId);
        createJobRequest.setOutputKeyPrefix(outputKeyPrefix);
        createJobRequest.setInput(input);
        createJobRequest.setOutput(output);

        amazonElasticTranscoder.createJob(createJobRequest);
    }

    public void transcode(String pipelineId, String outputKeyPrefix, JobInput input, Set<CreateJobOutput> outputs, Set<CreateJobPlaylist> playlists) {
        CreateJobRequest createJobRequest = new CreateJobRequest();
        createJobRequest.setPipelineId(pipelineId);
        createJobRequest.setOutputKeyPrefix(outputKeyPrefix);
        createJobRequest.setInput(input);
        createJobRequest.setOutputs(outputs);
        createJobRequest.setPlaylists(playlists);

        amazonElasticTranscoder.createJob(createJobRequest);
    }
}
