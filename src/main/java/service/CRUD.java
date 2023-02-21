package service;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import domain.TestPOJO;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;

public class CRUD {
    public static void main(String[] args) {
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY,"true");
        AmazonDynamoDB client =  AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        "http://localhost:4566",
                        "us-east-1"))
                .build();

//        DynamoDbClient client = DynamoDbClient.builder()
//                .endpointOverride(URI.create("http://localhost:8000"))
//                // The region is meaningless for local DynamoDb but required for client builder validation
//                .region(Region.US_EAST_1)
//                .credentialsProvider(StaticCredentialsProvider.create(
//                        AwsBasicCredentials.create("dummy-key", "dummy-secret")))
//                .build();


        DynamoDB dynamoDB = new DynamoDB(client);
        String table_name = "testDynamoDB";
        try{
            //Creating an item
            System.out.println("Create operation");
            createItem(client,table_name);


            //Reading an item
            System.out.println("Read operation");
            System.out.println(getItem(client,table_name).toString());


            //Updating an item
            System.out.println("Update operation");
            System.out.println(updateItem(client,table_name).toString());


            //Deleteing an item
            DynamoDBMapper mapper = new DynamoDBMapper(client);
            TestPOJO updatedItem = mapper.load(TestPOJO.class, 2018,"Book new");
            mapper.delete(updatedItem);
            System.out.println("Deleting item with the 300 id");
            TestPOJO deletedItem = mapper.load(TestPOJO.class, updatedItem.getYear(),updatedItem.getTitle());
            if (deletedItem == null) {
                System.out.println("Done - Sample item is deleted.");
            }
        }
        catch(Exception exc)
        {
            System.out.println(exc.getMessage());
        }
    }

    private static void createItem(AmazonDynamoDB client,String table_name)
    {

        DynamoDB dynamoDB = new DynamoDB(client);
        Table myTable = dynamoDB.getTable(table_name);
        System.out.println(myTable.describe());
        TestPOJO item = new TestPOJO();

        item.setTitle("Book 601");
        item.setYear(2018);

        DynamoDBMapper mapper = new DynamoDBMapper(client);
        mapper.save(item);
        System.out.println("Created item");
    }

    private static TestPOJO getItem(AmazonDynamoDB client,String table_name)
    {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        return mapper.load(TestPOJO.class, 2018,"Book 601");
    }

    private static TestPOJO updateItem(AmazonDynamoDB client,String table_name)
    {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        TestPOJO itemRetrieved = mapper.load(TestPOJO.class,2018,"Book 601");
        itemRetrieved.setTitle("Book new");
        mapper.save(itemRetrieved);
        return itemRetrieved;
    }

}
