package com.example.myapplication;

public class Admin {
    public Service createService(String name, String role){
        return new Service(name,role);
    }

    public void modifyService(){

    }

    public Service removeService(Service toDelete){
        Service temp = toDelete;
        toDelete = null;
        return temp;
    }

    public Account deleteEmployeeAccount(Employee toDelete){
        Employee temp = toDelete;
        toDelete = null;
        return temp;
    }

    public Account deletePatientAccount(Patient toDelete){
        Patient temp = toDelete;
        toDelete = null;
        return temp;
    }
}
