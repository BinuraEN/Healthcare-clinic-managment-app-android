package com.example.healthcarepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class viewAppointments_admin extends AppCompatActivity {

    admin a ;

    final String[] dbData = new String[]{"_id","image","date","patient_name","doctor_name","location_name"};
    final int[] uiElements = new int[]{R.id.txtAID,R.id.ivApImg,R.id.txtAppointmentDate,R.id.txtPatientID,R.id.txtDocName,R.id.txtLoc};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointments_admin);

        a= new admin(this);

        Cursor c = a.getAppointments();

        ListView appointments = findViewById(R.id.lstAppointmentsAdmin);
        appointments.setEmptyView(findViewById(R.id.empty));

        BaseAdapter adapter = new ImageCursorAdapter_admin(this,R.layout.appointment_admin_single_list_item,c,dbData,uiElements,0);
        adapter.notifyDataSetChanged();

        appointments.setAdapter(adapter);
    }
}
