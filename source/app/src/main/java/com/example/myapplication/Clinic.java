package com.example.myapplication;

public class Clinic {

    private String name; //name of the clinic
    private String address;
    public Clinic(String clinicName){
        this.name = clinicName;

    }

    public String getName(){
        return name;
    }
    public String getAddress(){
        return address;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAddress(String address){
        this.address = address;
    }


}

