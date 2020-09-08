package com.example.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;


public class MainActivity extends AppCompatActivity {

    public static String  EXTRA_MESSAGE = "12";
    public static String  EXTRA_ACCTYPE = "23";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDb;
    private String userID, name, acctype;
    private EditText emailID, password;
    private Button signIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        password = findViewById(R.id.password_Sign_in);
        emailID = findViewById(R.id.main_email);
        signIn = findViewById(R.id.log_in);


        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDb = mDatabase.getReference();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = auth.getCurrentUser();

                if (firebaseUser != null) {
                    Toast.makeText(MainActivity.this, "login successful", Toast.LENGTH_SHORT).show();

                    userID = firebaseUser.getUid();
                    //name = mDb.child("users").child(userID).child("firstName").getKey();

                    mDb.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("patient").child(userID).child("firstName").getValue(String.class) != null){
                                name = dataSnapshot.child("patient").child(userID).child("firstName").getValue(String.class);
                                acctype = "patient";
                                Intent a = new Intent(MainActivity.this, WelcomePage.class);
                                Bundle extras = new Bundle();
                                extras.putString(EXTRA_MESSAGE, name);
                                extras.putString(EXTRA_ACCTYPE, acctype);
                                a.putExtras(extras);
                                startActivity(a);

                            }
                            else if(dataSnapshot.child("employee").child(userID).child("firstName").getValue(String.class) != null){
                                final Intent a = new Intent(MainActivity.this, EmployeeProfile.class);
                                name = dataSnapshot.child("employee").child(userID).child("firstName").getValue(String.class);
                                acctype = "employee";
                                Bundle extras = new Bundle();
                                extras.putString(EXTRA_MESSAGE, name);
                                extras.putString(EXTRA_ACCTYPE, acctype);
                                a.putExtras(extras);
                                startActivity(a);

                            }else{
                                final Intent b = new Intent(MainActivity.this, adminSettings.class);
                                startActivity(b);

                            }
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }else{
                    Toast.makeText(MainActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };



        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailID.getText().toString().isEmpty()){
                    emailID.setError("Email id is missing");
                    emailID.requestFocus();
                }
                else  if(password.getText().toString().isEmpty()){
                    password.setError("Password is missing");
                    password.requestFocus();
                }
                else  if(emailID.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Both Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(emailID.getText().toString().isEmpty() && password.getText().toString().isEmpty())) {
                    auth.signInWithEmailAndPassword(emailID.getText().toString(), password.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login Error, Please Try Again", Toast.LENGTH_SHORT).show();
                            }
//                            else {
//                                Intent toHomePage = new Intent(MainActivity.this, WelcomePage.class);
//                                startActivity(toHomePage);
//                            }
                        }
                    });
                }
            }

        });
    }
    public void noAccount(View v){
        Intent intent = new Intent(this, SignUP.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        emailID.setText("");
        password.setText("");

        auth.signOut();
        auth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        auth.removeAuthStateListener(mAuthStateListener);
    }



}
