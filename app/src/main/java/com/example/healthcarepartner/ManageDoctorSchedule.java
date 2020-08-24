package com.example.healthcarepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ManageDoctorSchedule extends AppCompatActivity implements View.OnClickListener {

    admin a;

    static final int DIALOG_ID=0;
    int hour;
    int minute_x;

    String selectedLocation,selectedDoctor;

    DatePickerDialog picker;
    EditText date,time,fromTime,toTime;
    Spinner location,doctorName;
    Button add,view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctor_schedule);
        location=findViewById(R.id.spnLocation);
        doctorName = findViewById(R.id.spnDoctorName);


        location.setAdapter((SpinnerAdapter) loadLocationSpinnerData());
        //location.setOnItemSelectedListener( this);
        doctorName.setAdapter((SpinnerAdapter) loadDoctorNameSpinnerData());
        //doctorName.setOnItemSelectedListener( this);


        a = new admin(this);



        fromTime= findViewById(R.id.etxtFromTime);
        toTime = findViewById(R.id.etxtToTime);
        add=findViewById(R.id.btnAddSchedule);
        view = findViewById(R.id.btnViewSchedule);

        fromTime.setOnClickListener(this);
        toTime.setOnClickListener(this);

        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLocation= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        doctorName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDoctor=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //create datepicker dialog and get date to text field
        date = findViewById(R.id.etxtDate);
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ManageDoctorSchedule.this,android.R.style.Theme_Holo_Light_Dialog,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(0);
                        cal.set(year, monthOfYear, dayOfMonth);
                        Date chosenDate = cal.getTime();
                        if(chosenDate.compareTo(new Date())<0)
                        {
                            Toast.makeText(ManageDoctorSchedule.this, "Please Select a Date from today or ahead", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            String dateString = format.format(chosenDate);
                            date.setText(dateString);
                        }
                    }
                }, year, month, day);
                picker.show();
            }
        });//end datepicker dialog

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dName = selectedDoctor.trim();
                int d_id = a.getDoctorID(dName);

                String dt = date.getText().toString();

                String loc = selectedLocation.trim();
                int l_id = a.getLocationID(loc);

                String special = a.getDoctorSpeciality(dName);
                String from = fromTime.getText().toString();
                String to = toTime.getText().toString();

                try {
                    StringBuilder errors = fieldValidations(dt,dName,loc,from,to);
                    if(errors.length()!=0)
                    {
                        AlertDialog.Builder errorAlertBuilder = new AlertDialog.Builder(ManageDoctorSchedule.this);
                        errorAlertBuilder.setMessage(errors.toString());
                        errorAlertBuilder.setTitle("Error");
                        AlertDialog alert = errorAlertBuilder.create();
                        alert.show();
                    }
                    else
                    {
                        System.out.println(dName + " " + dt + " " + loc + " " + special + " " + from + " " + to);
                        long insert = a.addSchedule(dt, d_id, special, l_id, from, to);
                        if (insert > 0) {
                            Toast.makeText(ManageDoctorSchedule.this, "schedule updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ManageDoctorSchedule.this, "error while adding to schedule", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewScheduleAdmin.class));
            }
        });

    }


    @Override
   public void onClick(View v)
   {
        time = (EditText) v;
        time.setInputType(InputType.TYPE_NULL);
        showDialog(DIALOG_ID);
   }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id==DIALOG_ID)

            return new TimePickerDialog(ManageDoctorSchedule.this,android.R.style.Theme_Holo_Light_Dialog,TimePickerListner,hour,minute_x,true);

        else
            return null;
    }


    protected TimePickerDialog.OnTimeSetListener TimePickerListner = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            StringBuilder timeString= new StringBuilder();
            hour=hourOfDay;
            minute_x=minute;

            if(hour<10)
            {
                timeString.append("0"+hour);
                if(minute_x<10)
                {
                    timeString.append(":0"+minute_x+":00");
                }
                else
                {
                    timeString.append(":"+minute_x+":00");
                }
            }
            else
                {
                    timeString.append(hour);
                    if(minute_x<10)
                    {
                        timeString.append(":0"+minute_x+":00");
                    }
                    else
                    {
                        timeString.append(":"+minute_x+":00");
                    }
                }

                time.setText(timeString);
        }
    };


    public Adapter loadLocationSpinnerData() {

        ManageDoctorSchedule mds = new ManageDoctorSchedule();
        a=new admin(this);

        List<String> locations = a.getLocations();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,locations);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return dataAdapter;

    }

    public Adapter loadDoctorNameSpinnerData() {

        ManageDoctorSchedule mds = new ManageDoctorSchedule();
        a=new admin(this);

        List<String> names = a.getNames();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,names);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return dataAdapter;

    }

   public StringBuilder fieldValidations(String date,String name, String location, String fromTime, String toTime) throws ParseException {
        StringBuilder errors = new StringBuilder();


        if(date.isEmpty())
        {
            errors.append("Insert Date\n");
        }
        if (name.isEmpty())
        {
            errors.append("select doctor\n");
        }
        if(location.isEmpty())
        {
            errors.append("select location\n");
        }
        if(fromTime.isEmpty()||toTime.isEmpty())
        {
            errors.append("insert times\n");
        }
        else
        {
            Date t1 = new SimpleDateFormat("hh:mm:ss").parse(fromTime);
            Date t2 = new SimpleDateFormat("hh:mm:ss").parse(toTime);

            if(t1.compareTo(t2)>0)
            {
                errors.append("invalid time inputs\n");
            }
        }

        return errors;
   }

}
