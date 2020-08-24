package com.example.healthcarepartner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class viewMyAppointments extends AppCompatActivity {

    patient p;

    SimpleCursorAdapter adapter;

    final String[] dbData = new String[] {"_id","date","doctor_name","location_name","fromTime"};
    final int[] ui = new int[] {R.id.txtAID,R.id.txtApDate,R.id.txtDoctor,R.id.txtLocation,R.id.txtTime};

    int Patient_ID ;

    ListView myAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_appointments);
        p = new patient(this);

        Intent intent = getIntent();
        Patient_ID=intent.getIntExtra("P_ID",0);

        myAppointments = findViewById(R.id.lstMyAppointments);
        myAppointments.setEmptyView(findViewById(R.id.empty));

        Cursor appointmentData = p.getPatientAppointment(Patient_ID);

        adapter = new SimpleCursorAdapter(this,R.layout.view_my_appointment_single_list_item,appointmentData,dbData,ui,0);
        refreshAdapter();

        myAppointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView a_id = view.findViewById(R.id.txtAID);
                String aid = a_id.getText().toString();
                final int apId = Integer.parseInt(aid);

                AlertDialog.Builder builder = new AlertDialog.Builder(viewMyAppointments.this);
                builder.setMessage("D you want to cancel or delete  appointment number "+apId+" ?");
                builder.setPositiveButton("yes,delete/cancel appointment", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int delResult = p.deleteAppointment(apId);
                        if(delResult>0)
                        {
                            Toast.makeText(viewMyAppointments.this, "Appointment Deleted", Toast.LENGTH_SHORT).show();
                            refreshAdapter();

                        }
                        else
                        {
                            Toast.makeText(viewMyAppointments.this, "Error while deleting", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog deleteAlert = builder.create();
                deleteAlert.show();
            }
        });

    }

    public void refreshAdapter()
    {
        adapter.notifyDataSetChanged();
        myAppointments.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
