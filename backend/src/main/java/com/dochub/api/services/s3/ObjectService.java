package com.dochub.api.services.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.dochub.api.dtos.archive.ArchiveS3ResponseDTO;
import com.dochub.api.exceptions.s3.*;
import com.dochub.api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ObjectService {
    private final AmazonS3 s3Client;

    public Boolean doesObjectExist (final String bucketName, final String objectName) {
        return s3Client.doesObjectExist(bucketName, objectName);
    }

    public ArchiveS3ResponseDTO getObject (final String bucketName, final String objectName) {
        _validate(bucketName, objectName);

        try {
            final S3Object s3Object = s3Client.getObject(bucketName, objectName);
            final S3ObjectInputStream inputStream = s3Object.getObjectContent();

            byte[] fileContent = Utils.convertInputStreamToByteArray(inputStream);

            inputStream.close();

            return new ArchiveS3ResponseDTO(s3Object.getObjectMetadata().getContentType(), fileContent);
        } catch (Exception e) {
            throw new GetS3ObjectException(objectName, bucketName);
        }
    }

    public String generatePresignedUrl (final String bucketName, final String fileName, final String contentType) {
        if (!s3Client.doesBucketExistV2(bucketName)) {
            throw new BucketNotFoundException(bucketName);
        }

        try {
            final Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);

            final GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration)
                .withContentType(contentType);

            return s3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
        } catch (Exception e) {
            throw new PresignedUrlGenerationException();
        }
    }

    public void delete (final String bucketName, final String objectName) {
        _validate(bucketName, objectName);

        try {
            s3Client.deleteObject(bucketName, objectName);
        } catch (Exception e) {
            throw new DeleteObjectException(bucketName, objectName);
        }
    }

    private void _validate (final String bucketName, final String objectName) {
        if (!s3Client.doesBucketExistV2(bucketName)) {
            throw new BucketNotFoundException(bucketName);
        }

        if (!s3Client.doesObjectExist(bucketName, objectName)) {
            throw new ObjectNotFoundException(bucketName, objectName);
        }
    }
}