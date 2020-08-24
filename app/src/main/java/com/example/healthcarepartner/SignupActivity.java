package com.example.healthcarepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {
    patient p;
    DatePickerDialog picker;
    EditText pname;
    EditText pemail;
    EditText password;
    EditText confpassword;
    EditText DOB;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        p = new patient(this);

        //create datepicker dialog and get date to text field
        DOB = findViewById(R.id.etxtPDOB);
        DOB.setInputType(InputType.TYPE_NULL);
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(SignupActivity.this,android.R.style.Theme_Holo_Light_Dialog,new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                DOB.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );
                            }
                        }, year, month, day);
                picker.show();
            }
        });//end datepicker dialog

        //signup
        pname = findViewById(R.id.etxtPName);
        pemail = findViewById(R.id.etxtPEmail);
        password = findViewById(R.id.etxtPPassword);
        confpassword = findViewById(R.id.etxtConfPassword);

        btnSignUp = findViewById(R.id.btnPSignup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= pname.getText().toString().trim();
                String email = pemail.getText().toString().trim();
                String dob = DOB.getText().toString();
                String pass = password.getText().toString().trim();
                String confPass = confpassword.getText().toString().trim();

                StringBuilder errors = fieldValidation(name,email,dob,pass,confPass);
                if(errors.length()!=0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage(errors.toString());
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else
                {
                    long result= p.addUser(name,dob,email,pass);
                    if(result>0)
                    {
                        Toast.makeText(SignupActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        Intent backtoLogin = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(backtoLogin);
                    }
                    else
                    {
                        Toast.makeText(SignupActivity.this, "error occured while registration", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    public StringBuilder fieldValidation(String name, String email, String dob, String password, String confPassword)
    {
        StringBuilder sb = new StringBuilder();

        if(name.isEmpty() || email.isEmpty() || dob.isEmpty() || password.isEmpty())
        {
            sb.append("Fields cannot be Empty\n");
        }
        if(p.checkEmail(email))
        {
            sb.append("This Email is address already associated with an account\n");
        }
        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
        {
            sb.append("Invalid email address\n");
        }
        if(password.length()<8 || password.length()>12)
        {
            sb.append("Password should contain only 8-12 characters\n");
        }
        if(!password.equals(confPassword))
        {
            sb.append("Passwords should match\n");
        }

        return sb;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
