package com.keeperteacher.ktservice.aws.s3;

public class S3UploadProgress {

    private long bytesTransferred = 0;
    private final long totalBytes;

    public S3UploadProgress(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public long getBytesTransferred() {
        return bytesTransferred;
    }

    public void addBytesTransferred(long bytesTransferred) {
        this.bytesTransferred += bytesTransferred;
    }

    public double getPercentComplete() {
        return (bytesTransferred * 1.0 / totalBytes) * 100;
    }
}
