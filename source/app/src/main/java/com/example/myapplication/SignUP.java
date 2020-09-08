package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;


public class SignUP extends AppCompatActivity {
    public static String  EXTRA_MESSAGE = "12";
    public static String  EXTRA_ACCTYPE = "23";
    private FirebaseAuth auth;
    private DatabaseReference myDataBase;
    private FirebaseAuth.AuthStateListener listener;
    private Button create;
    private String accountType, gender;
    private CheckBox employee, patient, male, female ;
    private EditText emailID, password, name, lastname, age ;

    /**
     * Email validation pattern.
     */
    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    /**
     * Validates if the given input is a valid email address.
     *
     */
    public boolean validEmail(CharSequence email) {
        return (email != null && EMAIL_PATTERN.matcher(email).matches());
    }

    /**
     * Validates if the given input is a valid age.
     *
     */
    public boolean validAge(String age1) {
        if(age1 == null || age1.length()==0){
            return false;
        }else{
            if(age1.matches("[0-9]+")){
                return (Integer.parseInt(age1)>0);
            }return false;
        }
    }

    /**
     * Validates if the given input is a valid name.
     *
     */
    public boolean validName(String name1) {
        if(name1 == null){
            return false;
        }else{
            Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
            Matcher ms = ps.matcher(name1);
            return ms.matches();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        employee = findViewById(R.id.signup_employeeType);
        patient = findViewById(R.id.signup_patientType);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

        auth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.email_sign_up);
        password = findViewById(R.id.signup_password);
        create = findViewById(R.id.signup_create);
        age = findViewById(R.id.age);
        name = findViewById(R.id.editText);
        lastname = findViewById(R.id.surname);

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(auth.getCurrentUser()!=null){
                    Log.e("TAG", "on Auth state changed: authentication complete");

                }
            }
        };
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!patient.isChecked() && !employee.isChecked()){
                    Toast.makeText(SignUP.this,"acount type is not selected",Toast.LENGTH_SHORT).show();
                }
                else if(name.getText().toString().isEmpty()){
                    Toast.makeText(SignUP.this,"name field can not be empty",Toast.LENGTH_SHORT).show();
                }
                else if(lastname.getText().toString().isEmpty()){
                    Toast.makeText(SignUP.this,"last name field can not be empty",Toast.LENGTH_SHORT).show();
                }
                else if(emailID.getText().toString().isEmpty()){
                    Toast.makeText(SignUP.this,"email field can not be empty",Toast.LENGTH_SHORT).show();
                }
                else if(age.getText().toString().isEmpty()){
                    Toast.makeText(SignUP.this,"age field can not be empty",Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().isEmpty()){
                    Toast.makeText(SignUP.this,"password field can not be empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.createUserWithEmailAndPassword(emailID.getText().toString(),password.getText().toString()).addOnCompleteListener(SignUP.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(SignUP.this,"Sign up unsuccessful, please try again!",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Account acc;
                                if(patient.isChecked()){
                                    acc = new Patient();
                                }else{
                                    acc = new Employee();
                                }
                                myDataBase = FirebaseDatabase.getInstance().getReference();
                                acc.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                acc.setEmail(emailID.getText().toString());
                                acc.setFirstName(name.getText().toString());
                                acc.setLastname(lastname.getText().toString());
                                acc.setAge(parseInt(age.getText().toString()));
                                acc.setPassword(password.getText().toString());
                                if(patient.isChecked()){
                                    myDataBase.child("users").child("patient").child(acc.getId()).setValue(acc);
                                    accountType = "patient";
                                }
                                else{
                                    myDataBase.child("users").child("employee").child(acc.getId()).setValue(acc);
                                    accountType = "employee";
                                }
                                Bundle extras = new Bundle();
                                extras.putString(EXTRA_MESSAGE, name.getText().toString());
                                extras.putString(EXTRA_ACCTYPE, accountType);
                                if(accountType.equals("employee")){
                                    Intent intent = new Intent(SignUP.this, EmployeeProfile.class);
                                    intent.putExtras(extras);
                                    startActivity(intent);
                                }
                                else{
                                    Intent intent = new Intent(SignUP.this, WelcomePage.class);
                                    intent.putExtras(extras);
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                }
                }

        });
    }
    public void returnLogIn(View v){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();


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