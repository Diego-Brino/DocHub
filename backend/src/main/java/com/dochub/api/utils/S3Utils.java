package com.dochub.api.utils;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class S3Utils {
    public static String getFormattedBucketName (final String bucketName) {
        return String.format(Constants.BUCKET_NAME, bucketName).toLowerCase();
    }

    public static String generateBucketName () {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        if (uuid.length() > Constants.UUID_BUCKET_CHARACTER_LIMIT) {
            uuid = uuid.substring(0, Constants.UUID_BUCKET_CHARACTER_LIMIT);
        }

        return String.format(Constants.BUCKET_NAME, uuid);
    }

    public static ObjectMetadata buildMetadata (final MultipartFile file) {
        final ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        metadata.setContentDisposition("attachment");
        metadata.setCacheControl("public, max-age=31536000");
        metadata.addUserMetadata("original-filename", file.getOriginalFilename());

        return metadata;
    }
}