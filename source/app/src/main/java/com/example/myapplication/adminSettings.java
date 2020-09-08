package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class adminSettings extends AppCompatActivity {
    private DatabaseReference mDb;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseDatabase ref;
    private FirebaseAuth.AuthStateListener listener;
    private EditText clinicName, role, serviceName, serviceDescription,accType, email,password ;
    private TextView welcomeMessage;
    private String id,dPassword,dEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);
        ref = FirebaseDatabase.getInstance();
        mDb = ref.getReference();




        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(auth.getCurrentUser()!=null){
                    Log.e("TAG", "on Auth state changed: authentication complete");

                }
            }
        };
        Button buttonCreateService = (Button) findViewById(R.id.buttonCreateService);


        buttonCreateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), popCreateService.class);
                startActivity(i);
            }
        });
        buttonCreateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(adminSettings.this,R.style.popUpForm);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_create_service, viewGroup, false);
                Button closexbtn=dialogView.findViewById(R.id.closexbtn);
                Button continueCreate=dialogView.findViewById(R.id.continuebtnCreate);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                closexbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                continueCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clinicName = dialogView.findViewById(R.id.clinicNameCreate);
                        role = dialogView.findViewById(R.id.roleCreate);
                        serviceName = dialogView.findViewById(R.id.serviceNameCreate);
                        serviceDescription = dialogView.findViewById(R.id.serviceDescriptionCreate);
                        if(clinicName.getText().toString().length()<1 ||role.getText().toString().length()<1||serviceName.getText().toString().length()<1
                        ||serviceDescription.getText().toString().length()<1 ){
                            Toast.makeText(adminSettings.this, "you can not create a service with any of the fields empty", Toast.LENGTH_LONG).show();
                        }else if(!serviceName.getText().toString().equals("vaccination")&&!serviceName.getText().toString().equals("emergency")&&!serviceName.getText().toString().equals("prescription")
                        &&!serviceName.getText().toString().equals("laboratory")&&!serviceName.getText().toString().equals("consultation")&&!serviceName.getText().toString().equals("mental")&&!serviceName.getText().toString().equals("nutrition")&&!serviceName.getText().toString().equals("smoking")){
                            Toast.makeText(adminSettings.this, "available services are:emergency,vaccination,prescription,laboratory,consultation,mental,nutrition,smoking", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Service s = new Service(clinicName.getText().toString(), role.getText().toString());
                            s.setServiceName(serviceName.getText().toString());
                            s.setServiceDescription(serviceDescription.getText().toString());

                            mDb.child("Services").child(serviceName.getText().toString().toLowerCase()).child(clinicName.getText().toString()).setValue(s);//validate clinicname
                            Toast.makeText(adminSettings.this, "Service Cereated", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }



                    }
                });
                alertDialog.show();
                alertDialog.getWindow().setLayout(900, 1300);
            }
        });
        final AlertDialog.Builder builder = new AlertDialog.Builder(adminSettings.this,R.style.popUpForm);




        Button buttonRemoveService = (Button) findViewById(R.id.buttonRemoveService);


        buttonRemoveService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), popRemoveService.class);
                startActivity(i);
            }
        });
        buttonRemoveService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(adminSettings.this,R.style.popUpForm);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_remove_service, viewGroup, false);
                Button closexbtn=dialogView.findViewById(R.id.closexbtnRemove);
                Button continueRemove=dialogView.findViewById(R.id.continuebtnRemove);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                closexbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                continueRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clinicName = dialogView.findViewById(R.id.clinicNameRemove);
                        serviceName = dialogView.findViewById(R.id.serviceNameRemove);

                        mDb.child("Services").child(serviceName.getText().toString()).child(clinicName.getText().toString()).removeValue();
                        Toast.makeText(adminSettings.this, "Service removed", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                alertDialog.getWindow().setLayout(900, 1300);


            }
        });
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(adminSettings.this,R.style.popUpForm);




        Button buttonModifyService = (Button) findViewById(R.id.buttonModifyService);


//        buttonModifyService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), popActivity.class);
//                startActivity(i);
//            }
//        });
        buttonModifyService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(adminSettings.this,R.style.popUpForm);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_modify_service, viewGroup, false);
                Button closexbtn=dialogView.findViewById(R.id.closexbtnModify);
                Button continueModify = dialogView.findViewById(R.id.continuebtnModify);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                closexbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                continueModify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clinicName = dialogView.findViewById(R.id.clinicNameModify);
                        role = dialogView.findViewById(R.id.roleModify);
                        serviceName = dialogView.findViewById(R.id.serviceNameModify);
                        serviceDescription = dialogView.findViewById(R.id.serviceDescriptionModify);
                        final String sRole, sClinicName, sServiceName, sServiceDescription;
                        sClinicName = clinicName.getText().toString();
                        sRole = role.getText().toString();
                        sServiceName = serviceName.getText().toString();
                        sServiceDescription = serviceDescription.getText().toString();

                        mDb.child("Services").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                boolean flag1 = false;
                                boolean flag2 = false;
                                for(DataSnapshot child : dataSnapshot.getChildren()){
                                    if(child.getKey().equals(sServiceName)){
                                        flag1 = true;

                                    }
                                }if(flag1){
                                    for(DataSnapshot child1 : dataSnapshot.child(sServiceName).getChildren()){
                                        if(child1.getKey().equals(sClinicName)){
                                            flag2 = true;

                                        }
                                    }
                                }
                                if(!(flag2&&flag1)){
                                    Toast.makeText(adminSettings.this, "there is no such clinic or service", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    if(sRole.length() > 0){
                                        mDb.child("Services").child(sServiceName).child(sClinicName).child("role").setValue(sRole);


                                    }
                                    if(sServiceDescription.length()>0){
                                        mDb.child("Services").child(sServiceName).child(sClinicName).child("serviceDescription").setValue(sServiceDescription);

                                    }
                                    alertDialog.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(adminSettings.this, "role and service description has been changed", Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                alertDialog.getWindow().setLayout(900, 1300);
            }
        });
        final AlertDialog.Builder builder2 = new AlertDialog.Builder(adminSettings.this,R.style.popUpForm);

        Button adminServicesButton = (Button) findViewById(R.id.adminServices);

        adminServicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(adminSettings.this, AdminServices.class);
                startActivity(i);
            }
        });


        Button buttonDeleteAccount = (Button) findViewById(R.id.buttonDeleteEmployeeAccount);


        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), popDeleteAccount.class);
                startActivity(i);
            }
        });
        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(adminSettings.this,R.style.popUpForm);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_pop_delete_account, viewGroup, false);
                Button closexbtn=dialogView.findViewById(R.id.closexbtnDelete);
                Button continueDelete=dialogView.findViewById(R.id.continuebtnDelete);
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                closexbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                continueDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        accType = dialogView.findViewById(R.id.accountTypeDelete);
                        email = dialogView.findViewById(R.id.emailDelete);
                        mDb.child("users").child(accType.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot child : dataSnapshot.getChildren()){
                                    if(child.child("email").getValue(String.class).equals(email.getText().toString())){
                                        dEmail = child.child("email").getValue(String.class);
                                        dPassword = child.child("password").getValue(String.class);

                                    }
                                }if(dEmail == null || dPassword == null){
                                    Toast.makeText(adminSettings.this, "There is no such email address.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    auth.signInWithEmailAndPassword(dEmail, dPassword);
                                    final FirebaseUser user = auth.getCurrentUser();
                                    AuthCredential credential = EmailAuthProvider.getCredential(dEmail, dPassword);
                                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                mDb.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot child : dataSnapshot.child(accType.getText().toString()).getChildren()) {
                                                            if (child.child("email").getValue(String.class).equals(email.getText().toString())) {
                                                                id = child.getKey();
                                                                Log.v(null, id);
                                                            }

                                                        }

                                                        mDb.child("users").child(accType.getText().toString()).child(id).removeValue();

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                                Log.e("TAG", "onComplete: authentication complete");
                                                user.delete()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    Log.e("TAG", "User account deleted.");
                                                                    alertDialog.dismiss();
                                                                    Toast.makeText(adminSettings.this, "account deleted.", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Log.e("TAG", "User account deletion unsucessful.");
                                                                }
                                                            }
                                                        });
                                            } else {
                                                Toast.makeText(adminSettings.this, "Authentication failed",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                    }
                });
                alertDialog.show();
                alertDialog.getWindow().setLayout(900, 1300);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        auth.removeAuthStateListener(listener);
    }
}
