package com.shoppy.shoppy;

import java.io.Serializable;

/**
 * Created by James on 11/28/2017.
 */

public class Shopping_Item implements Serializable {
    private String name;
    private String image;
    private double price;
    private String description;
    private int itemID;
    private int amount;
    private int daysSinceLastBought;
    private String amazonLink;
    private int reason_resid;
    private String reason_text;

    public String getName(){
        return name;
    }

    public String getImage(){
        return image;
    }

    public double getPrice(){
        return price;
    }

    public String getDescription(){
        return description;
    }

    public int getAmount(){return amount;}

    public String getReason_text() {return reason_text; }

    public int getReason_resid() {return reason_resid; }

    public int getDaysSinceLastBought(){return daysSinceLastBought;}

    public void setName(String name){
        this.name = name;
    }

    public int getItemID(){
        return itemID;
    }

    public String getAmazonLink(){return amazonLink;}

    public void setImage(String image){
        this.image = image;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setItemID(int itemID){
        this.itemID = itemID;
    }

    public void setAmount(int amount){this.amount = amount;}

    public void setReason_text(String reason_text) {this.reason_text = reason_text;}

    public void setReason_resid(int reason_resid) {this.reason_resid = reason_resid; }

    public void setDaysSinceLastBought(int daysSinceLastBought){this.daysSinceLastBought = daysSinceLastBought;}

    public void setAmazonLink(String amazonLink){this.amazonLink = amazonLink;}

    public String toString() {
        return "["+name+" , "+amount+ "]";
    }

}
