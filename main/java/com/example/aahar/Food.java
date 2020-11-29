package com.example.aahar;

import java.util.List;

public class Food {
    private int food_id;
    private String title;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int restaurant_id;
    private String cuisine;
    private String price;
    private String discount;
    private String image;
    private String name;
    private  String location;
    private String city;
    private int quantity;

    public int getFood_id() {
        return food_id;
    }

    public String getTitle() {
        return title;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }

    public int getQuantity() {
        return quantity;
    }

    public Food(int food_id, String title, int restaurant_id, String cuisine, String price, String discount, String image, String name, String location, String city) {
        this.food_id = food_id;
        this.title = title;
        this.restaurant_id = restaurant_id;
        this.cuisine = cuisine;
        this.price = price;
        this.discount = discount;
        this.image = image;
        this.name = name;
        this.location = location;
        this.city = city;
    }
    public Food(int food_id, String title, int restaurant_id, String cuisine, String price, String discount, String image, String name, String location, String city,int quantity) {
        this.food_id = food_id;
        this.title = title;
        this.restaurant_id = restaurant_id;
        this.cuisine = cuisine;
        this.price = price;
        this.discount = discount;
        this.image = image;
        this.name = name;
        this.location = location;
        this.city = city;
        this.quantity=quantity;
    }
    public Food(int food_id, String title, int restaurant_id, String cuisine, String price, String discount, String image) {
        this.food_id = food_id;
        this.title = title;
        this.restaurant_id = restaurant_id;
        this.cuisine = cuisine;
        this.price = price;
        this.discount = discount;
        this.image = image;
    }



}
