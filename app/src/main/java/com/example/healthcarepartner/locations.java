package com.example.healthcarepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class locations extends AppCompatActivity {

    admin a;
    EditText name;
    EditText address;
    Button add;
    Button view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        a = new admin(this);

        name= findViewById(R.id.etxtDate);
        address = findViewById(R.id.etxtDocName);
        add= findViewById(R.id.btnAddLoc);
        view = findViewById(R.id.btnViewLoc);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locname = name.getText().toString();
                String locadd = address.getText().toString();

                if (locname.isEmpty() || locadd.isEmpty())
                {
                    Toast.makeText(locations.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                    {

                    long result = a.addLocation(locname, locadd);
                    if (result > 0) {
                        Toast.makeText(locations.this, "Location added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(locations.this, "Could not add location due to error", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewLocations.class));
            }
        });



    }
}
