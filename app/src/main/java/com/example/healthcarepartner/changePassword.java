package com.example.healthcarepartner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class changePassword extends AppCompatActivity {
    patient p;

    EditText email,pwd,confPwd;
    Button btnUpdatePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        p = new patient(this);

        email = findViewById(R.id.etxtEmailPEdit);
        pwd = findViewById(R.id.etxtPwdUpdate);
        confPwd = findViewById(R.id.etxtConfPwdUpdate);
        btnUpdatePass = findViewById(R.id.btnUpdatePass);

        btnUpdatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Pass = pwd.getText().toString().trim();
                String confPass = confPwd.getText().toString().trim();

                StringBuilder errors = fieldValidation(Email,Pass,confPass);
                if(errors.length()!= 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(changePassword.this);
                    builder.setMessage(errors.toString());
                    builder.setTitle("Error");

                    AlertDialog errorAlert = builder.create();
                    errorAlert.show();
                }
                else
                {
                    Boolean update = p.changePassword(Email,Pass);
                    if(update)
                    {
                        Toast.makeText(changePassword.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }
                    else
                    {
                        Toast.makeText(changePassword.this, "Error occured while changing password ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }
                }


            }
        });


    }

    public StringBuilder fieldValidation(String Email,String Password, String confPassword)
    {
        StringBuilder sb = new StringBuilder();

        if(Email.isEmpty() || Password.isEmpty() || confPassword.isEmpty())
        {
            sb.append("Fields cannot be empty\n");
        }
        if(Password.length()<8 || Password.length()>12)
        {
            sb.append("Password should contain only 8-12 characters\n");
        }
        if(!Password.equals(confPassword))
        {
            sb.append("Passwords should match\n");
        }

        return sb;
    }

}


