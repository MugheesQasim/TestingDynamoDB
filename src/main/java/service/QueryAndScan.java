package service;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import domain.TestPOJO;

import java.util.Iterator;
import java.util.Map;

public class QueryAndScan {

    public static void main(String[] args) {
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY,"true");
        AmazonDynamoDB client =  AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        "http://localhost:4566",
                        "us-east-1"))
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);
        String table_name = "testDynamoDB";

       // queryTable(table_name,client,dynamoDB);
        scanTable(table_name,client);
        System.out.println("Working class");
    }


    public static void scanTable(String tableName,AmazonDynamoDB client)
    {
        ScanRequest scanRequest = new ScanRequest()
                .withTableName(tableName);

        ScanResult result = client.scan(scanRequest);
        for (Map<String, AttributeValue> item : result.getItems()){
            System.out.println(item);
        }
    }

    public static void queryTable(String tableName,AmazonDynamoDB client,DynamoDB dynamoDB) {

        Table table = dynamoDB.getTable(tableName);

        QuerySpec spec = new QuerySpec().withHashKey("year","2018");

        ItemCollection<QueryOutcome> items = table.query(spec);
        if(items==null)
            System.out.println("Not working");
        else
            System.out.println(items.getAccumulatedItemCount());
        Iterator<Item> iterator = items.iterator();
        Item item = null;
        while (iterator.hasNext()) {
            item = iterator.next();
            System.out.println(item.toString());
        }
    }

}
