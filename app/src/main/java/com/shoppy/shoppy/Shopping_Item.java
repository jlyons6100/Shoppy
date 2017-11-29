package com.shoppy.shoppy;

/**
 * Created by James on 11/28/2017.
 */

public class Shopping_Item {
    private String name;
    private String image;
    private double price;
    private String description;

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
    public void setImage(String image){
        this.image = image;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setDescription(String description){
        this.description = description;
    }


}
