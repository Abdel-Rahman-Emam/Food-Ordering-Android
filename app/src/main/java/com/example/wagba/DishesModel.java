package com.example.wagba;

public class DishesModel {
    String dishName;
    String dishDesc;
    String dishPrice;
    String Image;
    public DishesModel() {}
    public DishesModel(String dishName, String dishDesc,String dishPrice, String image) {
        this.dishName = dishName;
        this.dishDesc = dishDesc;
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

    public String getDishDesc() {
        return dishDesc;
    }

    public void setDishDesc(String dishDesc) {
        this.dishDesc = dishDesc;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
