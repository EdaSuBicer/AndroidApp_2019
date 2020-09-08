package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeProfile extends AppCompatActivity {
    private DatabaseReference mDb;
    private FirebaseAuth.AuthStateListener listener;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseDatabase ref;
    private EditText clinicName, insurence, payment, phone, address;

    //private EditText clinicName, insurence, payment, phone, address ;
    static boolean CLICKED_ADD = false;
    static boolean CLICKED_REMOVE = false;
    private Button addService,removeService, clinicHours;

    private Button employee_profile_update_button;
    private static final String TAG = "EmployeeProfile";
    private boolean flag = false;
    String clinic,insurance,pay,pho,addre;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
         View view;
        ref = FirebaseDatabase.getInstance();
        mDb = ref.getReference();
        clinicName = findViewById(R.id.employee_profile_clinic_EditText);
        insurence = findViewById(R.id.employee_profile_insurence_EditText);
        payment = findViewById(R.id.employee_profile_payment_EditText);
        phone = findViewById(R.id.employee_profile_phone_EditText);
        address = findViewById(R.id.employee_profile_address_EditText);
        addService = findViewById(R.id.addServicesButton);
        clinicHours = findViewById(R.id.btnClinicHours);

        clinic = clinicName.getText().toString();
        insurance = insurence.getText().toString();
        pho = phone.getText().toString();
        addre = address.getText().toString();
        pay = payment.getText().toString();



        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeProfile.this, EmployeeAddServices.class);
                startActivity(intent);
                CLICKED_ADD = true;
            }
        });

        clinicHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeProfile.this, ActivityClinicHours.class);
                startActivity(intent);

            }
        });

        removeService = findViewById(R.id.removeServicesButton);
        removeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeProfile.this,EmployeeAddServices.class);
                startActivity(intent);
                CLICKED_REMOVE = true;
            }
        });
        mDb.child("users").child("employee").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("alreadyUpdated").getValue(Boolean.class)) {
                    String cn, ins, pmnt, adrs, phn;
                    cn = dataSnapshot.child("clinicName").getValue(String.class);
                    ins = dataSnapshot.child("insuranceType").getValue(String.class);
                    pmnt = dataSnapshot.child("paymentMethod").getValue(String.class);
                    adrs = dataSnapshot.child("address").getValue(String.class);
                    phn =  dataSnapshot.child("phoneNumber").getValue(String.class);
                    clinicName.setText(cn);
                    insurence.setText(ins);
                    payment.setText(pmnt);
                    address.setText(adrs);
                    phone.setText(phn);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        listener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if (auth.getCurrentUser() != null) {
//                    Log.e("TAG", "on Auth state changed: authentication complete");
//                }
//            }
//        };
//
//        mDb.child("Services").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String name,role,serviceDescription, finalString = null;
//                for(DataSnapshot child : dataSnapshot.getChildren()){
//                    for(DataSnapshot child1 : child.getChildren()){
//                        if(child1.getKey().equals(clinicName)){
//                            flag = true;
//                        }
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//        clinicName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (clinicName.getText().toString().isEmpty()) {
//                    Toast.makeText(EmployeeProfile.this, "Clinic Name field can not be empty", Toast.LENGTH_SHORT).show();
//                }else if(flag == false){
//                    Toast.makeText(EmployeeProfile.this,"No services found with this clinic name",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });




//        insurence.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isValid;
//                if (insurence.getText().toString().isEmpty()) {
//                    Toast.makeText(EmployeeProfile.this, "Insurance field can not be empty", Toast.LENGTH_SHORT).show();
//                }else{
//                    isValid = validInsuranceType(insurence.getText().toString());
//                    if(!isValid){
//                        Toast.makeText(EmployeeProfile.this, "Not a valid insurance type, try again", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//        });
//
//        payment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (payment.getText().toString().isEmpty()) {
//                    Toast.makeText(EmployeeProfile.this, "Payment field can not be empty", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (phone.getText().toString().isEmpty()) {
//                    Toast.makeText(EmployeeProfile.this, "Phone number field can not be empty", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (address.getText().toString().isEmpty()) {
//                    Toast.makeText(EmployeeProfile.this, "Address field can not be empty", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });
    }

    //  Validates if the given input is a valid clinic name.

    public boolean validAddress (String address){
        if (address == null) {
            return false;
        } else {
            String[] inp = address.split(",");
            if(inp.length!=2){
                return false;
            }else{
                Pattern ps = Pattern.compile("^[a-zA-Z_0-9]+$");
                Matcher ms = ps.matcher(inp[0]);
                Matcher ms1 = ps.matcher(inp[1]);
                return ms.matches() && ms1.matches();
            }

        }
    }
    public boolean validClinicName (String clinicName1){
        if (clinicName1 == null) {
            return false;
        } else {
            Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
            Matcher ms = ps.matcher(clinicName1);
            return ms.matches();
        }
    }


    //  Validates if the given input is a valid insurance type.

    public boolean validInsuranceType (String insurance1){
        if (insurance1 == null) {
            return false;
        } else {
            insurance1.toLowerCase();
            String validAnswer [] = { "uhip", "ohip", "private insurance"};
            for (int i = 0; i <= validAnswer.length -1; i++){
                if (insurance1.equals(validAnswer[i])){
                    return true;
                }
            }return false;
        }

    }


    //  Validates if the given input is a valid payment method.

    public boolean validPaymentMethod (String payment1){
        if (payment1 == null) {
            return false;
        } else {
            payment1.toLowerCase();
            String validAnswer [] = {"card", "debit", "cash"};
            for (int i = 0; i <= validAnswer.length -1; i++){
                if (payment1.equals(validAnswer[i])) {
                    return true;
                }
            }return false;
        }
    }


    // Validates if the given input is a valid phone number.

    public boolean validPhoneNumber (String phone1){
        if (phone1 == null) {
            return false;
        } else {
            Pattern ps = Pattern.compile("(1)?[0-9]{10}");
            Matcher ms = ps.matcher(phone1);
            return ms.matches();
        }
    }




    public boolean isAClinic(final String clinicName1){

        mDb.child("Services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    for(DataSnapshot child1 : child.getChildren()){
                        if(child1.getKey().equals(clinicName1)){
                            flag = true;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return flag;
    }
    public void update(View view){
        clinicName = findViewById(R.id.employee_profile_clinic_EditText);
        insurence = findViewById(R.id.employee_profile_insurence_EditText);
        payment = findViewById(R.id.employee_profile_payment_EditText);
        phone = findViewById(R.id.employee_profile_phone_EditText);
        address = findViewById(R.id.employee_profile_address_EditText);

        clinic = clinicName.getText().toString();
        insurance = insurence.getText().toString();
        pho = phone.getText().toString();
        addre = address.getText().toString();
        pay = payment.getText().toString();

        //boolean flag2 = isAClinic(clinicName.getText().toString());
        mDb.child("Clinics").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    if(child.getKey().equals(clinic)){
                        flag = true;
                    }
                }
                boolean isValidInsurance = false;
                boolean isValidPayment =false;
                boolean isValidPhoneNumber=false;
                boolean isValidAddress=false;
                if (clinic.isEmpty()) {
                    Toast.makeText(EmployeeProfile.this, "Clinic Name field can not be empty", Toast.LENGTH_SHORT).show();
                }else if(!flag){
                    Toast.makeText(EmployeeProfile.this,"No services found with this clinic name",Toast.LENGTH_SHORT).show();
                }
                if (insurance.isEmpty()) {
                    Toast.makeText(EmployeeProfile.this, "Insurance field can not be empty", Toast.LENGTH_SHORT).show();
                }else {
                    isValidInsurance = validInsuranceType(insurance);
                    if(!isValidInsurance){
                        Toast.makeText(EmployeeProfile.this, "Not a valid insurance type, try again", Toast.LENGTH_SHORT).show();
                    }
                }
                if (pay.isEmpty()) {
                    Toast.makeText(EmployeeProfile.this, "Payment field can not be empty", Toast.LENGTH_SHORT).show();
                }else {
                    isValidPayment = validPaymentMethod(pay);
                    if (!isValidPayment){
                        Toast.makeText(EmployeeProfile.this, "Not a valid payment method, try again", Toast.LENGTH_SHORT).show();
                    }
                }
                if (pho.isEmpty()) {
                    Toast.makeText(EmployeeProfile.this, "Phone number field can not be empty", Toast.LENGTH_SHORT).show();
                }else {
                    isValidPhoneNumber = validPhoneNumber(pho);
                    if (!isValidPhoneNumber){
                        Toast.makeText(EmployeeProfile.this, "Not a valid phone number, try again", Toast.LENGTH_SHORT).show();
                    }
                }
                if (addre.isEmpty()) {
                    Toast.makeText(EmployeeProfile.this, "Address field can not be empty", Toast.LENGTH_SHORT).show();
                }else{
                    isValidAddress = validAddress(addre);
                    if(!isValidAddress){
                        Toast.makeText(EmployeeProfile.this, "Not a valid address, try again", Toast.LENGTH_SHORT).show();
                    }
                }
                if(flag && isValidInsurance && isValidPayment && isValidPhoneNumber && isValidAddress){
                    mDb.child("users").child("employee").child(auth.getCurrentUser().getUid()).child("clinicName").setValue(clinicName.getText().toString());
                    mDb.child("users").child("employee").child(auth.getCurrentUser().getUid()).child("insuranceType").setValue(insurence.getText().toString());
                    mDb.child("users").child("employee").child(auth.getCurrentUser().getUid()).child("paymentMethod").setValue(payment.getText().toString());
                    mDb.child("users").child("employee").child(auth.getCurrentUser().getUid()).child("address").setValue(address.getText().toString());
                    mDb.child("users").child("employee").child(auth.getCurrentUser().getUid()).child("phoneNumber").setValue(phone.getText().toString());
                    Toast.makeText(EmployeeProfile.this, "account updated", Toast.LENGTH_LONG).show();
                    clinicName.setText("");
                    insurence.setText("");
                    payment.setText("");
                    address.setText("");
                    phone.setText("");

                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }





    protected void onStart(){
        super.onStart();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        String name = extras.getString("12");

        Toast.makeText(this, "Welcome " + name + "!", Toast.LENGTH_SHORT).show();
    }


    public void btnSchedule (View view){
        Intent intent = new Intent(this, Calendar_Activity.class);
        startActivity(intent);
    }
}
