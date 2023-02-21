package service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class S3Service {
    public static void main(String[] args) {

        String bucketName = "example2";

        createBucket2(bucketName);
        System.out.println("Bucket has been created with name: " + bucketName);
//        uploadFileToS3();
//        System.out.println("File successfully uploaded");
//        downloadFilesFromS3();
//        System.out.println("File successfully downloaded");
    }

//    public static void createBucket(String bucketName)
//    {
//        BasicAWSCredentials credentials = new BasicAWSCredentials("foo", "bar");
//        AwsClientBuilder.EndpointConfiguration endPointConfiguration = new AwsClientBuilder.EndpointConfiguration("http://localhost:4566","us-east-2");
//        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withEndpointConfiguration(endPointConfiguration).withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
//
//        try {
//            s3.createBucket(bucketName);
//        } catch (AmazonS3Exception e) {
//            System.err.println(e.getErrorMessage());
//        }
//    }

    public static void createBucket2(String bucketName)
    {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                "http://localhost:4566","us-east-1")).enablePathStyleAccess()
                    .build();

           // if (!s3Client.doesBucketExistV2(bucketName)) {
                // Because the CreateBucketRequest object doesn't specify a region, the
                // bucket is created in the region specified in the client.
                s3Client.createBucket(new CreateBucketRequest(bucketName,"us-east-1"));

                // Verify that the bucket was created by retrieving it and checking its location.
                String bucketLocation = s3Client.getBucketLocation(new GetBucketLocationRequest(bucketName));
                System.out.println("Bucket location: " + bucketLocation);
           // }
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it and returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }

    public static void uploadFileToS3()
    {
        String bucketName = "example";

        String fileName = "examplefile.txt";
        String filePath = "C:\\Users\\HotelKey\\Documents\\s3samplefiles\\" + fileName;

        AmazonS3 client = AmazonS3ClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                "http://localhost:4566",
                "us-east-1")).enablePathStyleAccess().build();

        PutObjectRequest request = new PutObjectRequest(bucketName,fileName,new File(filePath));
        client.putObject(request);
    }

    public static void downloadFilesFromS3()
    {
        AmazonS3 client = AmazonS3ClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                "http://localhost:4566",
                "us-east-1")).build();

        String bucketName = "example";

        String fileName = "examplefile.txt";
        String filePath = "C:\\Users\\HotelKey\\Documents\\s3samplefiles\\";

        GetObjectRequest request = new GetObjectRequest(bucketName,filePath);

        System.out.println(client.getObject(request).toString());
    }
}
