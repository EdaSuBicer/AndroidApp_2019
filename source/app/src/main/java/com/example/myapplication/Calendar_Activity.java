package com.example.myapplication;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendar_Activity extends AppCompatActivity {

    private CalendarView mCalendar;
    private Button cont, remover;
    private CheckBox ch, ch1, ch2;
    private TextView sched;
    String date, year, hours;
    private boolean isWorking = false;
    private DatabaseReference mDb;
    private FirebaseDatabase ref;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        ref = FirebaseDatabase.getInstance();
        mDb = ref.getReference();


        mCalendar = findViewById(R.id.calendarView);
        cont = findViewById(R.id.btncontinuesched);
        ch = findViewById(R.id.checkBox712);
        ch1 = findViewById(R.id.checkBox124);
        ch2 = findViewById(R.id.checkBox420);
        sched = findViewById(R.id.textViewSched);
        remover = findViewById(R.id.btnRemoveDay);





        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2 ){
                date = i + "/" + (i1 + 1) + "/" + i2;
                searchRemovable();

            }
        });


        showSched();
        searchRemovable();






    }
    public void btnCont(View view){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if(date == null) {
            date = sdf.format(new Date(mCalendar.getDate()));
        }
        if(remover.isEnabled()){
            mDb.child("users").child("employee").child(auth.getCurrentUser().getUid()).child("schedule").child(date).setValue("");
        }

        if(ch.isChecked()){
            hours = "7:00 - 12:00";
            isWorking = true;
        }
        if(ch1.isChecked() && isWorking == false){
            hours = "12:00 - 16:00";
            isWorking = true;
        } else if (ch1.isChecked() && isWorking == true){
            hours = hours.substring(0, hours.length() - 5);
            hours += "16:00";
        }
        if(ch2.isChecked() && isWorking == false){
            hours = "16:00 - 20:00";
            isWorking = true;
        }else if (ch2.isChecked() && isWorking == true){
            hours = hours.substring(0, hours.length() - 5);
            hours += "20:00";
        }
        if(ch.isChecked() && !ch1.isChecked() && ch2.isChecked()){
            isWorking = false;
            Toast.makeText(this,"You cannot have two separate shifts",Toast.LENGTH_SHORT).show();
        }

        if(isWorking){
            Toast.makeText(this,"Shift added to schedule",Toast.LENGTH_SHORT).show();


            mDb.child("users").child("employee").child(auth.getCurrentUser().getUid()).child("schedule").child(date).setValue(hours);
            isWorking = false;
            hours ="";

            date = sdf.format(new Date(mCalendar.getDate()));
            Toast.makeText(Calendar_Activity.this,date,Toast.LENGTH_SHORT).show();
            showSched();
            searchRemovable();

        }


    }

    public void remove(String y, String m, String d){
        Toast.makeText(Calendar_Activity.this,"Shift removed",Toast.LENGTH_SHORT).show();
        mDb.child("users").child("employee").child(auth.getCurrentUser().getUid()).child("schedule").child(y).child(m).child(d).removeValue();
        showSched();
        remover.setEnabled(false);
    }



    public void goBack(View view){

        finish();

    }

    public void showSched(){

        sched.setText("");
        mDb.child("users").child("employee").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("schedule").getValue() != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                    year = sdf.format(new Date(mCalendar.getDate()));
                    for(int k= Integer.parseInt(year); k <= Integer.parseInt(year)+1; k++ ){
                    for(int i = 1; i< 13; i++ ) {
                        for (int j = 1; j < 32; j++) {
                            if (dataSnapshot.child("schedule").child(Integer.toString(k)).child(Integer.toString(i)).child(Integer.toString(j)).getValue() != null) {
                                String data = k + "/";
                                data += i+ "/" + j + "/" + dataSnapshot.child("schedule").child(Integer.toString(k)).child(Integer.toString(i)).child(Integer.toString(j)).getValue(String.class);

                                sched.setText(sched.getText()
                                        + data + "\n");
                                data = "";
                            }

                        }
                    }

                    }

                }else{

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void searchRemovable(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if(date == null) {
            date = sdf.format(new Date(mCalendar.getDate()));
        }


        final String year, month, day;

        String segments[] = date.split("/");
        year = segments[segments.length - 3];
        month = segments[segments.length - 2];
        day = segments[segments.length - 1];

        mDb.child("users").child("employee").child(auth.getCurrentUser().getUid()).child("schedule").child(year).child(month).child(day).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                remover.setEnabled(true);
                    ch.setChecked(false);
                    ch1.setChecked(false);
                    ch2.setChecked(false);
                if(dataSnapshot.getValue().equals("7:00 - 12:00")){
                    ch.setChecked(true);
                } else if(dataSnapshot.getValue().equals("12:00 - 16:00")){
                    ch1.setChecked(true);
                } else if(dataSnapshot.getValue().equals("16:00 - 20:00")){
                    ch2.setChecked(true);
                } else if(dataSnapshot.getValue().equals("7:00 - 16:00")){
                    ch1.setChecked(true);
                    ch.setChecked(true);
                } else if(dataSnapshot.getValue().equals("12:00 - 20:00")){
                    ch1.setChecked(true);
                    ch2.setChecked(true);
                } else if(dataSnapshot.getValue().equals("7:00 - 20:00")){
                    ch.setChecked(true);
                    ch1.setChecked(true);
                    ch2.setChecked(true);
                }

                remover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        remove(year ,month, day);

                    }
                });

                }else{
                    remover.setEnabled(false);
                    ch.setChecked(false);
                    ch1.setChecked(false);
                    ch2.setChecked(false);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
