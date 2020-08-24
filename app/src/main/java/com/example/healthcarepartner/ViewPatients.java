package com.example.healthcarepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ViewPatients extends AppCompatActivity {

    admin a;

    ListView patientList;

    SimpleCursorAdapter adapter;

    final String[] dbData = new String[] {"_id","patient_name","DOB"};
    final int[] ui = new int[] {R.id.txtPatientID,R.id.txtPatientName,R.id.txtPatientDOB};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patients);
        a = new admin(this);

        patientList = findViewById(R.id.lstPatients);

        Cursor c = a.getPatientList();

        adapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.patients_single_list_item,c,dbData,ui,0);

        adapter.notifyDataSetChanged();
        patientList.setAdapter(adapter);
        patientList.setEmptyView(findViewById(R.id.empty));





    }
}
