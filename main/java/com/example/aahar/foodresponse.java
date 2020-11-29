package com.example.aahar;

import java.util.List;

public class foodresponse {

    private List<Food> food;
    public foodresponse( List<Food> food) {

        this.food = food;
    }
    public List<Food> getFood() {
        return food;
    }

}
