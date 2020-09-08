package com.example.myapplication;

public class Account {
    private String id;
    private String password;
    private String name;
    private String lastname;
    private String email;
    private int age;

    public String getId(){
        return id;
    }

    public String getPassword(){
        return password;
    }

    public String getFirstName(){
        return name;
    }

    public String getLastname(){
        return lastname;
    }

    public String getEmail(){
        return email;
    }

    public int getAge(){
        return age;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setFirstName(String name){
        this.name = name;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setAge(int age){
        this.age = age;
    }

}
