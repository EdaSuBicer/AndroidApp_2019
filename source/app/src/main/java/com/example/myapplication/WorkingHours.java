package com.example.myapplication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class WorkingHours {
    public Double mondayM, tuesdayM, wednesdayM,thursdayM, fridayM, saturdayM, sundayM,
            mondayE, tuesdayE, wednesdayE,thursdayE, fridayE, saturdayE, sundayE;
    private String name;
    private DatabaseReference mDb;
    private FirebaseDatabase ref;
    WorkingHours(String clinicName){


        this.name = clinicName;

    }

    public String getName(){
        return name;
    }

    public void setDay(String day, Double aM, Double pM){


        if(day.equals("mon")){
            mondayM = aM;
            mondayE = pM;

        }else if(day.equals("tue")){
            tuesdayM = aM;
            tuesdayE = pM;

        } else if(day.equals("wed")){
            wednesdayM = aM;
            wednesdayE = pM;

        } else if(day.equals("thur")){
            thursdayM = aM;
            thursdayE = pM;

        }else if(day.equals("fri")){
            fridayM = aM;
            fridayE = pM;

        }else if(day.equals("sat")){
            saturdayM = aM;
            saturdayE = pM;

        }else if(day.equals("sun")){
            sundayM = aM;
            sundayE = pM;

        }

    }

    private int getHours(String day){


        if(day.equals("mon")){


        }else if(day.equals("tue")){


        } else if(day.equals("wed")){


        } else if(day.equals("thur")){


        }else if(day.equals("fri")){

        }else if(day.equals("sat")){


        }else if(day.equals("sun")){

        }

        return -1;

    }



}
