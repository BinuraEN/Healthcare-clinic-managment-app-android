package com.example.healthcarepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ManageDoctors extends AppCompatActivity {
    admin a;
    EditText dName,dSpeciality;
    Button btnAdd,btnView;
    String name,spec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctors);

        a= new admin(this);

        dName=findViewById(R.id.etxtDate);
        dSpeciality=findViewById(R.id.etxtDocName);
        btnAdd = findViewById(R.id.btnAdd);
        btnView= findViewById(R.id.btnView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=dName.getText().toString();
                spec=dSpeciality.getText().toString();

                if (name.isEmpty() || spec.isEmpty())
                {
                    Toast.makeText(ManageDoctors.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    long result = a.addDoctor(name, spec);
                    if (result > 0) {
                        Toast.makeText(ManageDoctors.this, "doctor added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ManageDoctors.this, "Could not add doctor due to error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewDoctors.class));
            }
        });

    }
}
