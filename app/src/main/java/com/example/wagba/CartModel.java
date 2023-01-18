package com.example.wagba;

import java.io.Serializable;

public class CartModel implements Serializable {
    String dishName;
    String dishPrice;
    String Image;

    public CartModel(String dishName,String dishPrice, String image) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.Image = image;
    }

    public String getDishName() {
        return dishName;
    }
    public String getDishPrice() {
        return dishPrice;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setDishPrice(String dishPrice) {
        this.dishName = dishPrice;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
