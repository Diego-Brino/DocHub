package com.dochub.api.services.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.dochub.api.exceptions.s3.BucketAlreadyExistsException;
import com.dochub.api.exceptions.s3.CreateBucketException;
import com.dochub.api.exceptions.s3.DeleteBucketException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BucketService {
    private final AmazonS3 s3Client;

    public List<Bucket> getAll () {
        return s3Client.listBuckets();
    }

    public Boolean doesBucketExist (final String bucketName) {
        return s3Client.doesBucketExistV2(bucketName);
    }

    public void create (final String bucketName) {
        if (doesBucketExist(bucketName)) {
            throw new BucketAlreadyExistsException();
        }

        try {
            s3Client.createBucket(
                new CreateBucketRequest(bucketName)
                .withCannedAcl(CannedAccessControlList.Private)
            );
        } catch (Exception e) {
            throw new CreateBucketException();
        }
    }

    @Async
    public void deleteBucketWithContentsAsync (final String bucketName) {
        deleteBucketWithContents(bucketName);
    }

    public void deleteBucketWithContents (final String bucketName) {
        final ListObjectsV2Request listRequest = new ListObjectsV2Request().withBucketName(bucketName);

        ListObjectsV2Result listResult;

        try {
            do {
                listResult = s3Client.listObjectsV2(listRequest);
                final List<S3ObjectSummary> objectSummaries = listResult.getObjectSummaries();

                if (!objectSummaries.isEmpty()) {
                    final List<DeleteObjectsRequest.KeyVersion> keysToDelete = new ArrayList<>();

                    for (S3ObjectSummary os : objectSummaries) {
                        keysToDelete.add(new DeleteObjectsRequest.KeyVersion(os.getKey()));
                    }

                    final DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(bucketName).withKeys(keysToDelete);

                    s3Client.deleteObjects(deleteRequest);
                }

                listRequest.setContinuationToken(listResult.getNextContinuationToken());
            } while (listResult.isTruncated());

            s3Client.deleteBucket(bucketName);
        } catch (Exception e) {
            throw new DeleteBucketException();
        }
    }
}