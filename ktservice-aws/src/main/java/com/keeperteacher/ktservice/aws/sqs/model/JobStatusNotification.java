package com.keeperteacher.ktservice.aws.sqs.model;

import com.amazonaws.services.elastictranscoder.model.JobInput;
import com.amazonaws.services.elastictranscoder.model.JobOutput;

import java.util.List;

public class JobStatusNotification {

    private JobState state;
    private int errorCode;
    private String version;
    private String jobId;
    private String pipelineId;
    private JobInput input;
    private List<JobOutput> outputs;

    public JobState getState() {
        return state;
    }

    public void setState(JobState state) {
        this.state = state;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public JobInput getInput() {
        return input;
    }

    public void setInput(JobInput input) {
        this.input = input;
    }

    public List<JobOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<JobOutput> outputs) {
        this.outputs = outputs;
    }
}
