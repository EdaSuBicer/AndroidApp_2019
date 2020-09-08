package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Booking extends AppCompatActivity {

    private CalendarView mCalendar;
    private String clinicName, now;
    private long nbrOfPatients;
    private CheckBox ch, ch1, ch2;
    String date, year, hours, time;
    private boolean isOpen = false;
    private TextView peeps;
    private DatabaseReference mDb;
    private FirebaseDatabase ref;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        ref = FirebaseDatabase.getInstance();
        mDb = ref.getReference();




        mCalendar = findViewById(R.id.calendarView2);
        peeps = findViewById(R.id.textViewNbrOfPeeps);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        now = sdf.format(new Date());

        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2 ){
                date = i + "/" + (i1 + 1) + "/" + i2;
                findPeople();



            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = getIntent().getExtras();
        clinicName = extras.getString("SEARCHED_CLINIC_NAME");
        Toast.makeText(this, "Clinic Name: "+ clinicName, Toast.LENGTH_SHORT).show();
        date = now;
        findPeople();
    }

    public void book(View view){

        final String yearN, monthN, dayN, year, month, day;

        String segments[] = date.split("/");
        year = segments[segments.length - 3];
        month = segments[segments.length - 2];
        day = segments[segments.length - 1];

        String segment[] = now.split("/");
        yearN = segment[segment.length - 3];
        monthN = segment[segment.length - 2];
        dayN = segment[segment.length - 1];




        if(Integer.valueOf(yearN) <= Integer.valueOf(year)){
            if(Integer.valueOf(yearN).equals(Integer.valueOf(year))){
                if(Integer.valueOf(monthN) <= Integer.valueOf(month)) {
                    if(Integer.valueOf(monthN).equals(Integer.valueOf(month))){
                        if(Integer.valueOf(dayN) > Integer.valueOf(day)){
                            Toast.makeText(this, "you cannot time travel!", Toast.LENGTH_SHORT).show();
                        } else{
                            mDb.child("users").child("patient").child(auth.getCurrentUser().getUid()).child("bookings").child(date).setValue(date);
                            mDb.child("Clinics").child(clinicName).child("bookings").child(date).child(auth.getCurrentUser().getUid()).setValue("patient");
                            Toast.makeText(this, "Booking complete for " + date, Toast.LENGTH_SHORT).show();
                            findPeople();
                        }
                    }else{
                        mDb.child("users").child("patient").child(auth.getCurrentUser().getUid()).child("bookings").child(date).setValue(date);
                        mDb.child("Clinics").child(clinicName).child("bookings").child(date).child(auth.getCurrentUser().getUid()).setValue("patient");
                        Toast.makeText(this, "Booking complete for " + date, Toast.LENGTH_SHORT).show();
                        findPeople();
                    }
                }else{
                    Toast.makeText(this, "you cannot time travel!", Toast.LENGTH_SHORT).show();
                }
            }else {
                mDb.child("users").child("patient").child(auth.getCurrentUser().getUid()).child("bookings").child(date).setValue(date);
                mDb.child("Clinics").child(clinicName).child("bookings").child(date).child(auth.getCurrentUser().getUid()).setValue("patient");
                Toast.makeText(this, "Booking complete for " + date, Toast.LENGTH_SHORT).show();
                findPeople();
            }
        } else{
            Toast.makeText(this, "you cannot time travel!", Toast.LENGTH_SHORT).show();
        }




       }

       private void findPeople(){
           final String year, month, day;

           String segments[] = date.split("/");
           year = segments[segments.length - 3];
           month = segments[segments.length - 2];
           day = segments[segments.length - 1];


           mDb.child("Clinics").child(clinicName).child("bookings").child(year).child(month).child(day).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if(dataSnapshot.hasChildren()){
                       nbrOfPatients = dataSnapshot.getChildrenCount();
                       peeps.setText("Waiting time : " + nbrOfPatients*15 + " min");
                   }else{
                       peeps.setText("Waiting time : 0 min");
                   }


               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
       }

}
