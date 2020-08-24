package com.example.healthcarepartner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class AdminActivity extends AppCompatActivity  {

    CardView loc,doc,schedule,appointments,patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        loc = findViewById(R.id.locationsCard);
        doc = findViewById((R.id.doctorscard));
        schedule = findViewById(R.id.schedulecard);
        appointments=findViewById(R.id.appointmentsCard);
        patients= findViewById(R.id.patientsCard);

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toLocation = new Intent(getApplicationContext(),locations.class);
                startActivity(toLocation);

            }
        });

        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDoctor = new Intent (getApplicationContext(),ManageDoctors.class);
                startActivity(toDoctor);
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSchedule = new Intent(getApplicationContext(),ManageDoctorSchedule.class);
                startActivity((toSchedule));
            }
        });

        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appointmentsList = new Intent(getApplicationContext(),viewAppointments_admin.class);
                startActivity(appointmentsList);
            }
        });

        patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewPatients.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
