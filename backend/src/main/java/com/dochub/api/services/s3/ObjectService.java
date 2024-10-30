package com.dochub.api.services.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.dochub.api.dtos.s3.object.CreateObjectDTO;
import com.dochub.api.dtos.s3.object.DeleteObjectDTO;
import com.dochub.api.exceptions.s3.CreateObjectException;
import com.dochub.api.exceptions.s3.DeleteObjectException;
import com.dochub.api.utils.S3Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ObjectService {
    private final AmazonS3 s3Client;

    public List<S3ObjectSummary> getAll (final String bucketName) {
        final ObjectListing objectListing = s3Client.listObjects(S3Utils.getFormattedBucketName(bucketName));

        return objectListing.getObjectSummaries();
    }

    public void create (final CreateObjectDTO createObjectDTO) {
        try {
            final var putObjectRequest = new PutObjectRequest(
                S3Utils.getFormattedBucketName(createObjectDTO.bucketName()),
                createObjectDTO.file().getName(),
                createObjectDTO.file().getInputStream(),
                S3Utils.buildMetadata(createObjectDTO.file())
            ).withCannedAcl(CannedAccessControlList.Private);

            s3Client.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new CreateObjectException();
        }
    }

    public void delete (final DeleteObjectDTO deleteObjectDTO) {
        try {
            s3Client.deleteObject(S3Utils.getFormattedBucketName(deleteObjectDTO.bucketName()), deleteObjectDTO.objectName());
        } catch (Exception e) {
            throw new DeleteObjectException();
        }
    }
}