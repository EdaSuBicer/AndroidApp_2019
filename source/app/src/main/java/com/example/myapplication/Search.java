package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.graphics.Color;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Search extends AppCompatActivity {
    private EditText clinicSearch;
    private TextView found;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDb;
    private String searchBar, searchOption,cSearch,clinicName,serviceDesc,role;
    private Spinner searchBySpinner;
    private ListView listOfClinics;
    private HashMap<String,String> service ;
    private LinearLayout mainLayout;
    private static Boolean flag = false;
    private ArrayList<HashMap<String,String>> itemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mDatabase = FirebaseDatabase.getInstance();
        mDb = mDatabase.getReference();
        service = new HashMap<>();
        listOfClinics = findViewById(R.id.list_of_clinics_ClinicSearch);
        clinicSearch = findViewById(R.id.editTextClinicSearch);
        mainLayout = findViewById(R.id.search_layout);



        searchBySpinner = findViewById(R.id.search_by_spinner);
        String[] searchByOptions = {"Clinic Name","Working Hours","Address","Service Type"};
        ArrayAdapter options = new ArrayAdapter(Search.this, android.R.layout.simple_spinner_item, searchByOptions);
        searchBySpinner.setAdapter(options);

        searchBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    searchOption = "cName";
                }
                else if(position == 1){
                    searchOption = "hours";
                }
                else if(position == 2){
                    searchOption = "address";
                }
                else if(position == 3){
                    searchOption = "sName";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void search(View view){

        searchBar = clinicSearch.getText().toString();
        itemList.clear();
        service.clear();
        if(searchOption.equals("cName")){
            searchByClinicName();
        }
        else if(searchOption.equals("hours")){
            searchByWorkingHours();
        }
        else if(searchOption.equals("address")){
            searchByAddress();
        }
        else if(searchOption.equals("sName")){
            searchByServiceName();
        }
        if(listOfClinics.getAdapter() != null){

        }

        listOfClinics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < listOfClinics.getChildCount(); i++) {
                    if(position == i ){
                        listOfClinics.getChildAt(i).setBackgroundColor(Color.BLUE);
                    }else{
                        listOfClinics.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }

                HashMap<String,String> clicked = (HashMap<String, String>) listOfClinics.getItemAtPosition(position);
                String[] fLine = clicked.get("First Line").split("\\n");
                final String clinicName = fLine[0].trim().toLowerCase();
                if(!flag){
                    flag = true;
                    Button booking = new Button(Search.this);
                    booking.setLayoutParams(new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT));
                    mainLayout.addView(booking);
                    booking.setText("Book an Appointment");
                    booking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent in = new Intent(Search.this, Booking.class);
                            in.putExtra("SEARCHED_CLINIC_NAME", clinicName);
                            startActivity(in);
                        }
                    });
                }

            }
        });

    }
    private void searchByServiceName() {
        mDb.child("Services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cSearch = clinicSearch.getText().toString().trim().toLowerCase();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    if(child.getKey().equals(cSearch)){
                        for(DataSnapshot child1 : child.getChildren()){
                            clinicName = child1.getKey();
                            serviceDesc = "Service Description: " + child1.child("serviceDescription").getValue(String.class);
                            role ="Role of the person performing: " + child1.child("role").getValue(String.class);
                            service.put(clinicName,serviceDesc + "\n" + role);
                        }
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(Search.this,itemList,R.layout.list_item,
                        new String[]{"First Line","Second Line"},new int[]{R.id.text1,R.id.text2});
                Iterator iterator = service.entrySet().iterator();
                while(iterator.hasNext()){
                    HashMap<String,String> resultsMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry)iterator.next();
                    resultsMap.put("First Line",pair.getKey().toString());
                    resultsMap.put("Second Line",pair.getValue().toString());
                    itemList.add(resultsMap);
                }
                listOfClinics.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void searchByAddress() {
        mDb.child("Clinics").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cSearch = clinicSearch.getText().toString().trim();
                String serv =""; String address, phoneNo;
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    String[] a = child.child("address").getValue(String.class).split(",");
                    String postCode = a[1].trim();
                    if(postCode.equals(cSearch)){
                        address = "Address: "+child.child("address").getValue(String.class);
                        phoneNo = "Phone Number: "+child.child("phoneNumber").getValue(String.class);
                        clinicName = child.getKey()+"\n"+address+"\n"+phoneNo+"\nServices provided bt clinic: ";
                        for(DataSnapshot child1 : child.child("ListOfServices").getChildren()){
                            String sName = child1.getKey();
                            String sDescription = child1.child("serviceDescription").getValue(String.class);
                            serv = "    "+sName+": "+sDescription+"\n";

                        }
                        service.put(clinicName,serv);
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(Search.this,itemList,R.layout.list_item,
                        new String[]{"First Line","Second Line"},new int[]{R.id.text1,R.id.text2});
                Iterator iterator = service.entrySet().iterator();
                while(iterator.hasNext()){
                    HashMap<String,String> resultsMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry)iterator.next();
                    resultsMap.put("First Line",pair.getKey().toString());
                    resultsMap.put("Second Line",pair.getValue().toString());
                    itemList.add(resultsMap);
                }
                listOfClinics.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void searchByWorkingHours() {
        mDb.child("Clinic Hours").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cSearch = clinicSearch.getText().toString().trim().toLowerCase();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    String[] a = cSearch.split(",");
                    int morning, evening, searchedTime;
                    morning = child.child(a[0]+"M").getValue(Integer.class);
                    evening = child.child(a[0]+"E").getValue(Integer.class);
                    searchedTime = Integer.valueOf(a[1].trim());
                    if ((morning<searchedTime) && (evening>searchedTime)) {
                        clinicName = child.getKey();
                        service.put(clinicName, null);
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(Search.this,itemList,R.layout.list_item,
                        new String[]{"First Line","Second Line"},new int[]{R.id.text1,R.id.text2});
                Iterator iterator = service.entrySet().iterator();
                while(iterator.hasNext()){
                    HashMap<String,String> resultsMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry)iterator.next();
                    resultsMap.put("First Line",pair.getKey().toString());
                    resultsMap.put("Second Line",pair.getValue().toString());
                    itemList.add(resultsMap);
                }
                listOfClinics.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void searchByClinicName() {
        mDb.child("Clinics").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cSearch = clinicSearch.getText().toString();
                String serv =""; String address, phoneNo;
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    if(child.getKey().equals(cSearch)){
                        address = "Address: "+child.child("address").getValue(String.class);
                        phoneNo = "Phone Number: "+child.child("phoneNumber").getValue(String.class);
                        clinicName = child.getKey()+"\n"+address+"\n"+phoneNo+"\nServices provided bt clinic: ";
                        for(DataSnapshot child1 : child.child("ListOfServices").getChildren()){
                            String sName = child1.getKey();
                            String sDescription = child1.child("serviceDescription").getValue(String.class);
                            serv = serv+"    "+sName+": "+sDescription+"\n";

                        }
                        service.put(clinicName,serv);
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(Search.this,itemList,R.layout.list_item,
                        new String[]{"First Line","Second Line"},new int[]{R.id.text1,R.id.text2});
                Iterator iterator = service.entrySet().iterator();
                while(iterator.hasNext()){
                    HashMap<String,String> resultsMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry)iterator.next();
                    resultsMap.put("First Line",pair.getKey().toString());
                    resultsMap.put("Second Line",pair.getValue().toString());
                    itemList.add(resultsMap);
                }
                listOfClinics.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
