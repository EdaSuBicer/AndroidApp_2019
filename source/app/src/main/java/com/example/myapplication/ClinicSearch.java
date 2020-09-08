package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClinicSearch extends AppCompatActivity {

    private EditText clinicSearch;
    private TextView found;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDb;
    private String searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinic_search);

        mDatabase = FirebaseDatabase.getInstance();
        mDb = mDatabase.getReference();

        clinicSearch = findViewById(R.id.editTextClinicSearch);
        found = findViewById(R.id.textViewFound);



    }

    public void search(View view){

        searchBar = clinicSearch.getText().toString();

        mDb.child("Clinics").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(searchBar).getValue(String.class) != null){
                    found.setText(dataSnapshot.child(searchBar).child("address").getValue(String.class));

                }else{

                    found.setText("Sorry, no clinics found by this name");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
