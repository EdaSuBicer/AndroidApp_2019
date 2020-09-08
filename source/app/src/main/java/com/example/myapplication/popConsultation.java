package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class popConsultation extends AppCompatActivity {
    private TextView clinicName,role,serviceDescription;
    private String serviceName = "consultation";
    private String clinic_name, person_role, service_description;
    LinearLayout layout;
    private DatabaseReference mDb;
    private FirebaseDatabase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_consultation);

        ref = FirebaseDatabase.getInstance();
        mDb = ref.getReference();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.widthPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*1.2));

        layout = (LinearLayout) findViewById(R.id.linear_layout);


        mDb.child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int counter = 0;
                for(DataSnapshot child : dataSnapshot.getChildren()){

                    if(child.getKey().equals(serviceName)){

                        for(DataSnapshot child1 : child.getChildren()){
                            counter++;
                            clinic_name = child1.getKey();
                            person_role = child1.child("role").getValue(String.class);
                            service_description = child1.child("serviceDescription").getValue(String.class);
                            TextView textView0 = new TextView(popConsultation.this);
                            TextView textView1 = new TextView(popConsultation.this);
                            TextView textView2 = new TextView(popConsultation.this);
                            TextView textView3 = new TextView(popConsultation.this);

                            textView0.setText("Service "+String.valueOf(counter) +":");
                            textView1.setText("Clinic Name: " + clinic_name);
                            textView2.setText("Role: " + person_role);
                            textView3.setText("Service Description: " + service_description);
                            textView0.setLayoutParams(new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.MATCH_PARENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT));

                            textView0.setTextSize(25);
                            textView0.setTypeface(null, Typeface.BOLD);
                            textView1.setTextSize(20);
                            textView2.setTextSize(20);
                            textView3.setTextSize(20);

                            textView1.setLayoutParams(new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.MATCH_PARENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT));
                            textView2.setLayoutParams(new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.MATCH_PARENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT));
                            textView3.setLayoutParams(new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.MATCH_PARENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT));
                            layout.addView(textView0);
                            layout.addView(textView1);
                            layout.addView(textView2);
                            layout.addView(textView3);
                        }
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
