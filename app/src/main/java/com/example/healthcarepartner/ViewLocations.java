package com.example.healthcarepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ViewLocations extends AppCompatActivity {

    admin a;

    ListView locationsList;

    SimpleCursorAdapter adapter;

    final String[] dbData = new String[] {"_id","location_name","location_address"};
    final int[] ui = new int[] {R.id.txtLocationID,R.id.txtLocationName,R.id.txtLocationAddress};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_locations);
        a = new admin(this);

        locationsList = findViewById(R.id.lstLocations);

        Cursor c = a.getLocationsList();

        adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.locations_single_list_item, c, dbData, ui, 0);

        adapter.notifyDataSetChanged();
        locationsList.setAdapter(adapter);
        locationsList.setEmptyView(findViewById(R.id.empty));
    }
}
