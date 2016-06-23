package com.keeperteacher.ktservice.aws.sqs;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keeperteacher.ktservice.aws.sqs.model.JobStatusNotification;
import com.keeperteacher.ktservice.aws.sqs.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AwsSqsService {

    private static final Logger LOG = LoggerFactory.getLogger(AwsSqsService.class);
    private static final int MAX_NUMBER_OF_MESSAGES = 5;
    private static final int VISIBILITY_TIMEOUT = 15;
    private static final int WAIT_TIME_SECONDS = 15;
    private AWSCredentials credentials;
    private AmazonSQS amazonSQS;
    private Set<String> queueUrls;
    @Autowired private ObjectMapper objectMapper;

    public AwsSqsService() {
        Region region = Region.getRegion(Regions.US_WEST_2);
        credentials = new ProfileCredentialsProvider().getCredentials();
        amazonSQS = new AmazonSQSClient();
        amazonSQS.setRegion(region);
        queueUrls = new HashSet<>();
    }

    public void poll(String queueUrl, JobStatusNotificationHandler handler) throws IOException {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                .withQueueUrl(queueUrl)
                .withMaxNumberOfMessages(MAX_NUMBER_OF_MESSAGES)
                .withVisibilityTimeout(VISIBILITY_TIMEOUT)
                .withWaitTimeSeconds(WAIT_TIME_SECONDS);

        while(queueUrls.contains(queueUrl)) {
            List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
            if(messages == null) continue;
            messages.stream().forEach(message -> {
                try {
                    JobStatusNotification notification = parseNotification(message.getBody());
                    handler.handle(notification);
                } catch (IOException e) {
                    LOG.error("Failed to deserialize notification: " + message);
                }
            });
        }

        LOG.info("polling stopped for: " + queueUrl);
        JobStatusNotification notification = parseNotification("");
    }

    public void cancel(String queueUrl) {
        queueUrls.remove(queueUrl);
    }


    private JobStatusNotification parseNotification(String body) throws IOException {
        Notification<JobStatusNotification> notification = objectMapper.readValue(body, new TypeReference<Notification<JobStatusNotification>>() {});
        return notification.getMessage();
    }
}
