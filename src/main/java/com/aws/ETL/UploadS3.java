package com.aws.ETL;

import java.io.File;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class UploadS3
{
    public static void uploadFiles(String files[])
    {
    	String AWS_ACCESS_KEY_ID = "AKIAIGWC2ETZBWMRST4Q";
    	String AWS_SECRET_KEY ="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";
    	String clientRegion = "us-east-2";
        String bucketName = "etldata2019";
        //String fileObjKeyName = "80801205";

        try {
        	BasicAWSCredentials awsCreds = new BasicAWSCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .build();
        
            
            // Upload a file as a new object with ContentType and title specified.
            for ( String fileName : files) {
            	System.out.println(fileName);
            	int startIndex = fileName.lastIndexOf('\\');
            	int endIndex = fileName.lastIndexOf('.');
            	String fileObjKeyName = fileName.substring(startIndex+1, endIndex);
            	PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            	ObjectMetadata metadata = new ObjectMetadata();
            	metadata.setContentType("plain/text");
            	metadata.addUserMetadata("x-amz-meta-title", "someTitle");
            	request.setMetadata(metadata);
            	s3Client.putObject(request);
            }
        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }
}
