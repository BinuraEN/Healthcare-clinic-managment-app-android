package com.example.healthcarepartner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PatientActivity extends AppCompatActivity {
    patient p;
    int PatientID;
    String pName;

    CardView cdMakeAppointment,cdMyAppointments,cdDoctors;
    TextView P_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        p= new patient(this);

        Intent intent = getIntent();
        PatientID = intent.getIntExtra("P_ID",0);
        System.out.println(intent.getIntExtra("P_ID",0));


        P_Name=findViewById(R.id.txtPatientID);
        pName=p.getPatientName(PatientID);
        P_Name.setText(pName);


        cdMakeAppointment = findViewById(R.id.cardMakeAppointment);
        cdMyAppointments = findViewById(R.id.cardMyAppointments);
        cdDoctors = findViewById(R.id.cardDocList);

        cdMakeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewSchedule = new Intent(getApplicationContext(),ViewSchedule.class);
                viewSchedule.putExtra("P_ID",PatientID);
                System.out.println(PatientID);
                startActivity(viewSchedule);
            }
        });

        cdMyAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAppointments = new Intent(getApplicationContext(),viewMyAppointments.class);
                myAppointments.putExtra("P_ID",PatientID);
                startActivity(myAppointments);
            }
        });

        cdDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewDoctors.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
