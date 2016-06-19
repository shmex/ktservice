package com.keeperteacher.ktservice.aws.s3;

public class AwsS3TransferFailedException extends Exception {
    public AwsS3TransferFailedException() {
    }

    public AwsS3TransferFailedException(String message) {
        super(message);
    }

    public AwsS3TransferFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AwsS3TransferFailedException(Throwable cause) {
        super(cause);
    }

    public AwsS3TransferFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
