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

}
