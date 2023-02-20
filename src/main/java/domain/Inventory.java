package domain;


import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="inventory")
public class Inventory {

    private Integer id;
    private String itemName;
    private int itemQuantity;
    private String[] itemDetails;

    @DynamoDBHashKey(attributeName="id")
    public Integer getId() { return id; }
    public void setId(Integer id) {this.id = id; }

    @DynamoDBAttribute(attributeName="item_name")
    public String getItemName() {return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    @DynamoDBAttribute(attributeName="item_quantity")
    public int getItemQuantity() { return itemQuantity; }
    public void setItemQuantity(int itemQuantity) { this.itemQuantity = itemQuantity; }

    @DynamoDBAttribute(attributeName="item_details")
    public String[] getItemDetails() { return itemDetails; }
    public void setItemDetails(String[] itemDetails) { this.itemDetails = itemDetails; }

    @Override
    public String toString() {
        return "Item [item quantity=" + itemQuantity + ", itemDetails=" + itemDetails + ", id=" + id + ", itemName=" + itemName + "]";
    }
}