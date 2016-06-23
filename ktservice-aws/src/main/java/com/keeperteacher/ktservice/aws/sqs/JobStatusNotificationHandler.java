package com.keeperteacher.ktservice.aws.sqs;

import com.keeperteacher.ktservice.aws.sqs.model.JobStatusNotification;

public interface JobStatusNotificationHandler {
    void handle(JobStatusNotification jobStatusNotification);
}
