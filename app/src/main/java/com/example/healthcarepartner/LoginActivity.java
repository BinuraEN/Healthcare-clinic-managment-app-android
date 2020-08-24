package com.example.healthcarepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    patient p;

    Button btnSignup;
    Button btnLogin;
    EditText etxtEmail;
    EditText etxtPass;
    TextView editPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        p = new patient(this);

        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPass = findViewById(R.id.etxtPassword);
        editPass = findViewById(R.id.txtForgotPassword);

        //sign up
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupActivity = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(signupActivity);
            }
        });//end sign up

        //log in
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etxtEmail.getText().toString().trim();
                String pwd = etxtPass.getText().toString().trim();

                if(email.isEmpty() || pwd.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please fill in the Feilds", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(pwd.length()<8 && pwd.equals("pass") && email.equals("admin@healthcare.com") )
                    {
                        Intent toAdminDashBoard = new Intent(getApplicationContext(),AdminActivity.class);
                        startActivity(toAdminDashBoard);
                    }
                    else
                        {
                            boolean result = false;
                            Cursor patient = p.patientLogin(email, pwd);
                            if(patient.getCount()>0)
                            {
                                result = true;
                            }
                            if (result == true)
                            {
                                int pid = 0;
                                while (patient.moveToNext())
                                {
                                    int idIndex = patient.getColumnIndex("P_ID");
                                    pid = patient.getInt(idIndex);
                                }
                                Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                Intent toPatientDashBoard = new Intent(getApplicationContext(), PatientActivity.class);
                                toPatientDashBoard.putExtra("P_ID", pid);
                                System.out.println(pid);
                                startActivity(toPatientDashBoard);
                            }
                            else if (result == false)
                            {
                                Toast.makeText(LoginActivity.this, "no user found", Toast.LENGTH_SHORT).show();
                                System.out.println(email);
                                System.out.println(pwd);
                            }
                        }
                }

            }
        });

        //change password
        editPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePass = new Intent(getApplicationContext(),changePassword.class);
                startActivity(changePass);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
