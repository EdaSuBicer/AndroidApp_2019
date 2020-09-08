package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EmployeeAddServices extends AppCompatActivity {
    private ListView list;
    private DatabaseReference mDb;
    private FirebaseDatabase ref;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String clinicName,address,phone;

    public void returnEmployeeProfile(View v){
        EmployeeProfile.CLICKED_ADD = false;
        EmployeeProfile.CLICKED_REMOVE = false;
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add_services);

        ref = FirebaseDatabase.getInstance();
        mDb = ref.getReference();
        list = findViewById(R.id.listView2);

        final HashMap<String,String> service = new HashMap<>();
        //final ArrayList<HashMap<String,String>> hashes = new ArrayList<>();
        final String iD = auth.getCurrentUser().getUid();

        mDb.child("users").child("employee").child(iD).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clinicName = dataSnapshot.child("clinicName").getValue(String.class);
                address = dataSnapshot.child("address").getValue(String.class);
                phone = dataSnapshot.child("phoneNumber").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if(EmployeeProfile.CLICKED_ADD == true){
            mDb.child("Services").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name,role,serviceDescription, finalString = null;
                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        name = "Service Name: " + child.getKey();
                        for(DataSnapshot child1 : child.getChildren()){
                            if(child1.getKey().equals(clinicName)){
                                role = "Role of the person performing: " + child1.child("role").getValue(String.class);
                                serviceDescription = "Service Description: " + child1.child("serviceDescription").getValue(String.class);
                                finalString = role + "\n" + serviceDescription;
                                service.put(name,finalString);
                            }

                        }
                    }

                    ArrayList<HashMap<String,String>> itemList = new ArrayList<>();
                    SimpleAdapter adapter = new SimpleAdapter(EmployeeAddServices.this,itemList,R.layout.list_item,
                            new String[]{"First Line","Second Line"},new int[]{R.id.text1,R.id.text2});

                    Iterator iterator = service.entrySet().iterator();
                    while(iterator.hasNext()){
                        HashMap<String,String> resultsMap = new HashMap<>();
                        Map.Entry pair = (Map.Entry)iterator.next();
                        resultsMap.put("First Line",pair.getKey().toString());
                        resultsMap.put("Second Line",pair.getValue().toString());
                        itemList.add(resultsMap);
                    }
                    list.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    for (int i = 0; i < list.getChildCount(); i++) {
                        if(position == i ){
                            list.getChildAt(i).setBackgroundColor(Color.BLUE);
                        }else{
                            list.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                        }
                    }

                    HashMap<String,String> a = (HashMap<String, String>) list.getItemAtPosition(position);
                    String[] whatever = a.get("Second Line").split("\\n");
                    String[] whatever2 = a.get("First Line").split(":");

                    String[] hah = whatever[0].split(":");
                    String role = hah[1].substring(1);
                    String[] hah2 = whatever[1].split(":");
                    String description = hah2[1].substring(1);
                    final String serName = whatever2[1].substring(1);
                    Service newService  = new Service(serName,role);
                    newService.setServiceDescription(description);

                    mDb.child("users").child("employee").child(iD).child("ListOfServices").child(serName).setValue(newService);
                    mDb.child("Clinics").child(clinicName).child("address").setValue(address);
                    mDb.child("Clinics").child(clinicName).child("phoneNumber").setValue(phone);
                    mDb.child("Clinics").child(clinicName).child("ListOfServices").child(serName).child("role").setValue(role);
                    mDb.child("Clinics").child(clinicName).child("ListOfServices").child(serName).child("serviceDescription").setValue(description);
                    Toast.makeText(EmployeeAddServices.this, "Service successfully added!", Toast.LENGTH_SHORT).show();

                }
            });
        }else if(EmployeeProfile.CLICKED_REMOVE){
            mDb.child("users").child("employee").child(iD).child("ListOfServices").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name, role, serviceDescription, finalString = null;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        name = "Service Name: " + child.getKey();
                        role = "Role of the person performing: " + child.child("role").getValue(String.class);
                        serviceDescription = "Service Description: " + child.child("serviceDescription").getValue(String.class);
                        finalString = role + "\n" + serviceDescription;
                        service.put(name, finalString);
                    }


                    ArrayList<HashMap<String, String>> itemList = new ArrayList<>();
                    SimpleAdapter adapter = new SimpleAdapter(EmployeeAddServices.this, itemList, R.layout.list_item,
                            new String[]{"First Line", "Second Line"}, new int[]{R.id.text1, R.id.text2});

                    Iterator iterator = service.entrySet().iterator();
                    while (iterator.hasNext()) {
                        HashMap<String, String> resultsMap = new HashMap<>();
                        Map.Entry pair = (Map.Entry) iterator.next();
                        resultsMap.put("First Line", pair.getKey().toString());
                        resultsMap.put("Second Line", pair.getValue().toString());
                        itemList.add(resultsMap);
                    }
                    list.setAdapter(adapter);

                }
                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError){
                    }
            });

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    for (int i = 0; i < list.getChildCount(); i++) {
                        if(position == i ){
                            list.getChildAt(i).setBackgroundColor(Color.BLUE);
                        }else{
                            list.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                        }
                    }

                    HashMap<String,String> a = (HashMap<String, String>) list.getItemAtPosition(position);
                    String[] whatever = a.get("Second Line").split("\\n");
                    String[] whatever2 = a.get("First Line").split(":");

                    String[] hah = whatever[0].split(":");
                    String role = hah[1].substring(1);
                    String[] hah2 = whatever[1].split(":");
                    String description = hah2[1].substring(1);
                    final String serName = whatever2[1].substring(1);
                    Service newService  = new Service(serName,role);
                    newService.setServiceDescription(description);

                    mDb.child("users").child("employee").child(iD).child("ListOfServices").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            /*if(mDb.child("users").child("employee").child(iD).child("ListOfServices").child("emergency")==null&&mDb.child("users").child("employee").child(iD).child("ListOfServices").child("vaccination")==null&&
                                    mDb.child("users").child("employee").child(iD).child("ListOfServices").child("prescription")==null&&mDb.child("users").child("employee").child(iD).child("ListOfServices").child("consultation")==null
                            &&mDb.child("users").child("employee").child(iD).child("ListOfServices").child("nutrition")==null&&mDb.child("users").child("employee").child(iD).child("ListOfServices").child("smoking")==null
                            &&mDb.child("users").child("employee").child(iD).child("ListOfServices").child("mental")==null&&mDb.child("users").child("employee").child(iD).child("ListOfServices").child("laboratory")==null){
                                Toast.makeText(EmployeeAddServices.this,"No services associated with your clinic name...",Toast.LENGTH_SHORT).show();
                            }else{*/
                                for(DataSnapshot child : dataSnapshot.getChildren()){
                                    if(child.getKey().equals(serName)){
                                        mDb.child("users").child("employee").child(iD).child("ListOfServices").child(child.getKey()).removeValue();

                                        mDb.child("Clinics").child(clinicName).child("ListOfServices").child(serName).removeValue();
                                        Toast.makeText(EmployeeAddServices.this, "Service successfully removed!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            //}

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });
        }


    }
}
