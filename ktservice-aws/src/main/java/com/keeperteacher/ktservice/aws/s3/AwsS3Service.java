package com.keeperteacher.ktservice.aws.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.Transfer;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

@Service
public class AwsS3Service {

    private static final Logger LOG = LoggerFactory.getLogger(AwsS3Service.class);
    private AWSCredentials awsCredentials;
    private AmazonS3 s3client;

    public AwsS3Service() {
        refreshCredentials();
    }

    public void refreshCredentials() {
        awsCredentials = new ProfileCredentialsProvider().getCredentials();
        s3client = new AmazonS3Client(awsCredentials);
        s3client.setRegion(Region.getRegion(Regions.US_WEST_2));
    }

    public void uploadFile(String bucketName, String key, File file, ProgressListener progressListener) throws AwsS3TransferFailedException, InterruptedException {
        TransferManager transferManager = new TransferManager(awsCredentials);
        transferManager.getAmazonS3Client().setRegion(Region.getRegion(Regions.US_WEST_2));

        PutObjectRequest request = new PutObjectRequest(bucketName, key, file);
        request.setGeneralProgressListener(progressListener);

        Upload upload = transferManager.upload(request);

        upload.waitForCompletion();
        if(upload.getState() != Transfer.TransferState.Completed) {
            throw new AwsS3TransferFailedException();
        }
    }

    public void deleteFile(String bucketName, String key) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucketName, key);
        s3client.deleteObject(request);
    }

    public void createFolder(String bucketName, String folderName) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(0);

        InputStream noContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest request = new PutObjectRequest(bucketName, folderName, noContent, objectMetadata);
        s3client.putObject(request);
    }

    public void deleteFolder(String bucketName, String folderName) {
        for(S3ObjectSummary file : s3client.listObjects(bucketName, folderName).getObjectSummaries()) {
            deleteFile(bucketName, file.getKey());
        }
        deleteFile(bucketName, folderName);
    }
}
