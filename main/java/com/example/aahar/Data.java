package com.example.aahar;

public class Data {
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String number;
    public String password;
    public String name;
    public Data(String number,String password,String name){
        this.number=number;
        this.password=password;
        this.name=name;
    }


}
