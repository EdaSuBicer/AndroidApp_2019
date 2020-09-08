package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ActivityClinicHours extends AppCompatActivity {
    private EditText openTime, closeTime;
    private int Otime, Ctime;
    private CheckBox Mon, Tue, Wed, Thu, Fri, Sat, Sun;
    private Double morning, evening;
    private WorkingHours clinicHours;
    private Button submit;
    private FirebaseDatabase ref;
    private DatabaseReference mDb;
    private String clinicName;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_hours);

        ref = FirebaseDatabase.getInstance();
        mDb = ref.getReference();

        openTime = findViewById(R.id.editTextTimeOpen);
        closeTime = findViewById(R.id.editTextTimeClose);

        Mon = findViewById(R.id.checkBoxMon);
        Tue = findViewById(R.id.checkBoxTue);
        Wed = findViewById(R.id.checkBoxWed);
        Thu = findViewById(R.id.checkBoxThu);
        Fri = findViewById(R.id.checkBoxFri);
        Sat = findViewById(R.id.checkBoxSat);
        Sun = findViewById(R.id.checkBoxSun);

        submit = findViewById(R.id.btnSubmit);
        mDb.child("users").child("employee").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.child("clinicName").getValue() != null) {
                    clinicName = dataSnapshot.child("clinicName").getValue().toString();

                    mDb.child("Clinic Hours").child(clinicName).child("Open Hours").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            if(dataSnapshot.hasChildren()){
                                if(!dataSnapshot.child("mondayM").getValue().toString().equals("-1")){


                                    closeTime.setText(dataSnapshot.child("mondayE").getValue().toString());
                                    openTime.setText(dataSnapshot.child("mondayM").getValue().toString());
                                    Mon.setChecked(true);
                                }
                                if(!dataSnapshot.child("tuesdayM").getValue().toString().equals("-1")){

                                    closeTime.setText(dataSnapshot.child("tuesdayE").getValue().toString());
                                    openTime.setText(dataSnapshot.child("tuesdayM").getValue().toString());
                                    Tue.setChecked(true);
                                }
                                if(!dataSnapshot.child("wednesdayM").getValue().toString().equals("-1")){

                                    closeTime.setText(dataSnapshot.child("wednesdayE").getValue().toString());
                                    openTime.setText(dataSnapshot.child("wednesdayM").getValue().toString());
                                    Wed.setChecked(true);
                                }
                                if(!dataSnapshot.child("thursdayM").getValue().toString().equals("-1")){

                                    closeTime.setText(dataSnapshot.child("thursdayE").getValue().toString());
                                    openTime.setText(dataSnapshot.child("thursdayM").getValue().toString());
                                    Thu.setChecked(true);
                                }
                                if(!dataSnapshot.child("fridayM").getValue().toString().equals("-1")){

                                    closeTime.setText(dataSnapshot.child("fridayE").getValue().toString());
                                    openTime.setText(dataSnapshot.child("fridayM").getValue().toString());
                                    Fri.setChecked(true);
                                }
                                if(!dataSnapshot.child("saturdayM").getValue().toString().equals("-1")){

                                    closeTime.setText(dataSnapshot.child("saturdayE").getValue().toString());
                                    openTime.setText(dataSnapshot.child("saturdayM").getValue().toString());
                                    Sat.setChecked(true);
                                }
                                if(!dataSnapshot.child("sundayM").getValue().toString().equals("-1")){

                                    closeTime.setText(dataSnapshot.child("sundayE").getValue().toString());
                                    openTime.setText(dataSnapshot.child("sundayM").getValue().toString());
                                    Sun.setChecked(true);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void submit(View view){

        morning = Double.valueOf(openTime.getText().toString().replaceAll("\\s", "").replace(':', '.'));
        evening = Double.valueOf(closeTime.getText().toString().replaceAll("\\s", "").replace(':', '.'));

        Toast.makeText(this, "Hours Updated" , Toast.LENGTH_SHORT).show();
        mDb.child("users").child("employee").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.child("clinicName").getValue() != null) {
                    clinicName = dataSnapshot.child("clinicName").getValue().toString();
                }

                clinicHours = new WorkingHours(clinicName);

                if(Mon.isChecked()){
                    clinicHours.setDay("mon", morning, evening);
                }else{
                    clinicHours.setDay("mon", -1.0, -1.0);
                }
                if(Tue.isChecked()){
                    clinicHours.setDay("tue", morning, evening);
                }else{
                    clinicHours.setDay("tue", -1.0, -1.0);
                }
                if(Wed.isChecked()){
                    clinicHours.setDay("wed", morning, evening);
                }else{
                    clinicHours.setDay("wed", -1.0, -1.0);
                }
                if(Thu.isChecked()){
                    clinicHours.setDay("thur", morning, evening);
                }else{
                    clinicHours.setDay("thur", -1.0, -1.0);
                }
                if(Fri.isChecked()){
                    clinicHours.setDay("fri", morning, evening);
                }else{
                    clinicHours.setDay("fri", -1.0, -1.0);
                }
                if(Sat.isChecked()){
                    clinicHours.setDay("sat", morning, evening);
                }else{
                    clinicHours.setDay("sat", -1.0, -1.0);
                }
                if(Sun.isChecked()){
                    clinicHours.setDay("sun", morning, evening);
                }else{
                    clinicHours.setDay("sun", -1.0, -1.0);
                }

                mDb.child("Clinic Hours").child(clinicName).child("Open Hours").setValue(clinicHours);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
