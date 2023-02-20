import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.Arrays;
import java.util.Iterator;

public class LocalStackMain {
    public static void main(String[] args) {
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY,"true");
        AmazonDynamoDB client =  AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        "http://localhost:4566",
                        "us-east-1"))
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);
        String table_name = "testDynamoDB1";
        try{
            System.out.println("Creating Table...");
            Table table = dynamoDB.createTable(table_name,
                    Arrays.asList(new KeySchemaElement("year", KeyType.HASH),
                            new KeySchemaElement("title",KeyType.RANGE)),
                    Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                            new AttributeDefinition("title",ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L,10L));

            System.out.println("Table: " + table_name
                    + " Status: " + (table.getDescription() != null
                    ? table.getDescription().getTableStatus():
                    "Inactive!!"));

            table.waitForActive();
            System.out.println("Table: " + table_name + " Status: " + table.getDescription().getTableStatus());

        }catch(Exception exp){
            System.out.println("Operation Failed :(");
            System.out.println(exp.getMessage());

        }
        listMyTables(dynamoDB);
    }

    static void listMyTables(DynamoDB dynamoDB) {

        TableCollection<ListTablesResult> tables = dynamoDB.listTables();
        Iterator<Table> iterator = tables.iterator();

        System.out.println("Listing table names");

        while (iterator.hasNext()) {
            Table table = iterator.next();
            System.out.println(table.getTableName());
        }
    }
}
