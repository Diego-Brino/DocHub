package com.dochub.api.utils;

import java.util.UUID;

public class S3Utils {
    public static String generateBucketName () {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        if (uuid.length() > Constants.UUID_BUCKET_CHARACTER_LIMIT) {
            uuid = uuid.substring(0, Constants.UUID_BUCKET_CHARACTER_LIMIT);
        }

        return String.format(Constants.BUCKET_NAME, uuid);
    }

    public static String generateFileName () {
        return UUID.randomUUID().toString();
    }
}