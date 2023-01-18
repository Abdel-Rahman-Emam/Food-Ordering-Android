package com.example.wagba;

public class RestaurantsModel {
    String restaurantName;
    String restaurantStatus;
    String restaurantImage;
    public RestaurantsModel(){}
    public RestaurantsModel(String restaurantName, String restaurantStatus, String image) {
        this.restaurantName = restaurantName;
        this.restaurantStatus = restaurantStatus;
        this.restaurantImage = image;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(String restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }
}
