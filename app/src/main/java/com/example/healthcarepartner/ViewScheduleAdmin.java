package com.example.healthcarepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewScheduleAdmin extends AppCompatActivity {

    admin a;


    private ListView scheduleList;
    private SimpleCursorAdapter adapter;
    final String[] dbData = new String[]{"_id","date","doctor_name","speciality","location_name","fromTime","toTime"};
    final int[] uiElement = new int[] {R.id.txtSID,R.id.txtDate,R.id.txtDocName,R.id.txtspeciality,R.id.txtLocation,R.id.txtFromTime,R.id.txtToTime};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        Intent intent = getIntent();

        scheduleList = findViewById(R.id.lstSchedule);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //System.out.println("date : "+date);

        a = new admin(this);


        Cursor cursor = a.getSchedule();

        adapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.schedule_single_list_item,cursor,dbData,uiElement,0);

        adapter.notifyDataSetChanged();
        scheduleList.setAdapter(adapter);
        scheduleList.setEmptyView(findViewById(R.id.empty));


    }
}

