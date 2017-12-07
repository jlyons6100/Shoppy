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
    private  int amount;
    private int daysSinceLastBought;

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

    public int getDaysSinceLastBought(){return daysSinceLastBought;}

    public void setName(String name){
        this.name = name;
    }

    public int getItemID(){
        return itemID;
    }

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

    public void setDaysSinceLastBought(int daysSinceLastBought){this.daysSinceLastBought = daysSinceLastBought;}


    public String toString() {
        return "["+name+" , "+amount+ "]";
    }

}
