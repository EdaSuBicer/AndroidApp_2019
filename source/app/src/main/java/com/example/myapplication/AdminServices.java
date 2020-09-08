package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminServices extends AppCompatActivity {

    private Button vaccButton,presButton,labButton,consultButton,emergencyButton, mentalButton,nutritionButton,smokingButton;
    private boolean countVacc,countEm,countLab,countPres,countConsult,countMental,countNutrition,countSmoking = false;
    private DatabaseReference mDb;
    private FirebaseDatabase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_services);

        ref = FirebaseDatabase.getInstance();
        mDb = ref.getReference();

        vaccButton = findViewById(R.id.vaccinationButton);
        presButton = findViewById(R.id.PrescriptionAsButton);
        labButton = findViewById(R.id.DiagnosticLabsButton);
        consultButton = findViewById(R.id.ConsultationButton);
        emergencyButton = findViewById(R.id.EmergencyButton);
        mentalButton = findViewById(R.id.MentalHealthButton);
        nutritionButton = findViewById(R.id.NutritionButton);
        smokingButton = findViewById(R.id.smokingButton);


        mDb.child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    if(child.getKey().equals("vaccination")){
                        countVacc = true;
                    }
                    if(child.getKey().equals("emergency")){
                        countEm= true;
                    }
                    if(child.getKey().equals("laboratory")){
                        countLab = true;
                    }
                    if(child.getKey().equals("consultation")){
                        countConsult = true;
                    }
                    if(child.getKey().equals("prescription")){
                        countPres = true;
                    }if(child.getKey().equals("mental")){
                        countMental = true;
                    }if(child.getKey().equals("nutrition")){
                        countNutrition = true;
                    }if(child.getKey().equals("smoking")){
                        countSmoking = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        vaccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminServices.this,R.style.popUpForm);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView;

                if(countVacc){
                    Intent i = new Intent(getApplicationContext(),popVaccination.class);
                    startActivity(i);
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_vaccination, viewGroup, false);
                }else{
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_no_service, viewGroup, false);
                    final AlertDialog alertDialog = builder.create();
                    builder.setView(dialogView);
                    alertDialog.show();
                }
                //Button closexbtn=dialogView.findViewById(R.id.closexbtn);
                builder.setView(dialogView);
                //final AlertDialog alertDialog = builder.create();

                //alertDialog.show();
            }
            });

        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminServices.this,R.style.popUpForm);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView;

                if(countEm){
                    Intent i = new Intent(getApplicationContext(),popEmergency.class);
                    startActivity(i);
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_emergency, viewGroup, false);
                }else{
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_no_service, viewGroup, false);
                    final AlertDialog alertDialog = builder.create();
                    builder.setView(dialogView);
                    alertDialog.show();
                }
                builder.setView(dialogView);
                //final AlertDialog alertDialog = builder.create();

                //alertDialog.show();
            }
        });

        presButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminServices.this,R.style.popUpForm);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView;

                if(countPres){
                    Intent i = new Intent(getApplicationContext(),popPrescription.class);
                    startActivity(i);
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_prescription, viewGroup, false);
                }else{
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_no_service, viewGroup, false);
                    builder.setView(dialogView);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                builder.setView(dialogView);

                //final AlertDialog alertDialog = builder.create();

                //alertDialog.show();
            }
        });

        labButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminServices.this,R.style.popUpForm);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView;

                if(countLab == true){
                    Intent i = new Intent(getApplicationContext(),popLab.class);
                    startActivity(i);
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_lab, viewGroup, false);
                }else{
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_no_service, viewGroup, false);
                    builder.setView(dialogView);
                    final AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                }
                builder.setView(dialogView);
                //final AlertDialog alertDialog = builder.create();

                //alertDialog.show();
            }
        });

        consultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminServices.this,R.style.popUpForm);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView;

                if(countConsult){
                    Intent i = new Intent(getApplicationContext(),popConsultation.class);
                    startActivity(i);
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_consultation, viewGroup, false);
                }else{
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_no_service, viewGroup, false);
                    builder.setView(dialogView);
                    final AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                }


            }
        });

        mentalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminServices.this,R.style.popUpForm);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView;

                if(countMental){
                    Intent i = new Intent(getApplicationContext(),popMental.class);
                    startActivity(i);
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_mental, viewGroup, false);
                }else{
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_no_service, viewGroup, false);
                    builder.setView(dialogView);
                    final AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                }
                //Button closexbtn=dialogView.findViewById(R.id.closexbtn);
                builder.setView(dialogView);
            }
        });

        nutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminServices.this,R.style.popUpForm);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView;

                if(countNutrition){
                    Intent i = new Intent(getApplicationContext(),popNutrition.class);
                    startActivity(i);
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_nutrition, viewGroup, false);
                }else{
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_no_service, viewGroup, false);
                    builder.setView(dialogView);
                    final AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                }
                builder.setView(dialogView);
            }
        });

        smokingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminServices.this,R.style.popUpForm);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView;

                if(countSmoking){
                    Intent i = new Intent(getApplicationContext(),popSmoking.class);
                    startActivity(i);
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_smoking, viewGroup, false);

                    //startActivity(new Intent(AdminServices.this,popSmoking.class));
                }else{
                    startActivity(new Intent(AdminServices.this,popNoService.class));
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_no_service, viewGroup, false);
                    builder.setView(dialogView);
                    final AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                }
                builder.setView(dialogView);
            }
        });



    }
}





