package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class WelcomePage extends AppCompatActivity {

    protected TextView welcomeMessage;
    protected String name, message,accType;
    private FirebaseAuth auth;
    private Button signOutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        welcomeMessage = findViewById(R.id.welcome_message);

        signOutButton = findViewById(R.id.sign_out);

      signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), popRateClinic.class);
                startActivity(i);
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(WelcomePage.this,R.style.popUpForm);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.activity_pop, viewGroup, false);

                Button submitButton = dialogView.findViewById(R.id.submitButton);
                Button cancelButton = dialogView.findViewById(R.id.cancelButton);
                final RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);

                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                        final Intent b = new Intent(WelcomePage.this, MainActivity.class);
                        startActivity(b);

                    }
                });

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String starsTotal = "Total Stars: " + ratingBar.getNumStars();
                        String rating = "Rating: " + ratingBar.getRating();
                        Toast.makeText(getApplicationContext(), starsTotal + "\n" + rating, Toast.LENGTH_LONG).show();

                        //final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                        //executorService.scheduleAtFixedRate( rating, 0, 1, TimeUnit.SECONDS);

                        final Intent b = new Intent(WelcomePage.this, MainActivity.class);
                        startActivity(b);

                    }
                });

                //submit button clic
                // submitButton.setOnClickListener(new View.OnClickListener() {
                //@Override
                //public void onClick(View v) {
                //   clicked = true;
                //}
                // });


                //if (clicked){
                //String starsTotal = "Total Stars: " + ratingBar.getNumStars();
                //  String rating = "Rating: " + ratingBar.getRating();
                // Toast.makeText(getApplicationContext(), starsTotal + "\n" + rating, Toast.LENGTH_LONG).show();
                // dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.activity_pop, viewGroup, false);
                //auth.signOut();
                //   } //else{
                //dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.activity_pop, viewGroup, false);
                //  auth.signOut();
                //  }

                // builder.setView(dialogView);


                alertDialog.show();
                alertDialog.getWindow().setLayout(900, 1300);
            }
        });

    }

    public void sign_out(View v){

        //Intent intent2 = new Intent(this, popRateClinic.class);
        //startActivity(intent2);

        Intent intent = new Intent(this, popRateClinic.class);
        startActivity(intent);
        finish();
    }


    public void searchClinic(View v){

        Intent intent = new Intent(this, Search.class);
        startActivity(intent);

    }

    protected void onStart(){
        super.onStart();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        name = extras.getString("12");
        accType = extras.getString("23");


        welcomeMessage.setText("Welcome " + name + "\n You are signed in as a " + accType);


    }


}
