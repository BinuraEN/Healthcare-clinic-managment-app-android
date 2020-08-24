package com.example.healthcarepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ViewDoctors extends AppCompatActivity {
    admin a;

    ListView doctorsList;

    SimpleCursorAdapter adapter;

    final String[] dbData = new String[] {"_id","doctor_name","speciality"};
    final int[] ui = new int[] {R.id.txtDocID,R.id.txtDocName,R.id.txtDocSpecialty};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctors);
        a = new admin(this);

        doctorsList = findViewById(R.id.lstDoctors);

        Cursor c = a.getDoctorsList();

        adapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.doctors_single_list_item,c,dbData,ui,0);

        adapter.notifyDataSetChanged();
        doctorsList.setAdapter(adapter);
        doctorsList.setEmptyView(findViewById(R.id.empty));

    }
}
