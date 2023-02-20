package domain;


import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName="testDynamoDB")
public class TestPOJO {

    private int year;
    private String title;

    public TestPOJO() {
    }

    @DynamoDBHashKey(attributeName="year")
    public int getYear() {return year; }
    public void setYear(int year) { this.year = year; }

    @DynamoDBRangeKey(attributeName="title")
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @Override
    public String toString() {
        return "Item [title=" + title + ", year=" + year + ", id=" + "id" + "]";
    }
}