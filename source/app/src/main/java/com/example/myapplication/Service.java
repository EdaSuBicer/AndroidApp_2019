package com.example.myapplication;

public class Service implements Viewer{

    private String name; //name of the clinic
    private String role; //role of the person performing the service
    private String serviceName, serviceDescription;
    public Service(String serviceName, String role){
        this.serviceName = serviceName;
        this.role = role;
    }

    public String getServiceName(){return serviceName; }
    public String getServiceDescription(){return serviceDescription; }
    public String getName(){
        return name;
    }
    public String getRole(){
        return role;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setRole(String role){
        this.role = role;
    }
    public void setServiceName(String serviceName){this.serviceName = serviceName;}
    public void setServiceDescription(String serviceDescription){this.serviceDescription = serviceDescription;}


}
