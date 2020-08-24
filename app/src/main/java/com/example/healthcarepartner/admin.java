package com.example.healthcarepartner;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;


public class admin {

    DatabaseHelper dbhelper;
    SQLiteDatabase db;
    Context context;

    public admin(Context c)
    {
        context=c;
    }

    public admin open()
    {
        dbhelper=new DatabaseHelper(context);
        db=dbhelper.getWritableDatabase();
        return this;
    }

    public long addLocation(String name, String address)
    {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("location_name", name);
        contentValues.put("location_address", address);
        long res = db.insert("locations", null, contentValues);
        db.close();
        return res;

    }

    public long addDoctor(String name,String speciality)
    {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("doctor_name", name);
        contentValues.put("speciality", speciality);
        long res = db.insert("doctor", null, contentValues);
        db.close();
        return res;
    }

    public long addSchedule(String date,int d_id,String docSpec,int l_id,String from,String to)
    {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date",date);
        contentValues.put("d_id",d_id);
        contentValues.put("speciality",docSpec);
        contentValues.put("l_id",l_id);
        contentValues.put("fromTime",from);
        contentValues.put("toTime",to);
        long res = db.insert("doctor_schedule", null, contentValues);
        db.close();
        return res;
    }


    public List<String> getLocations(){
        List<String> list = new ArrayList<>();
        Cursor cursor;

        String[] columns = new String[]{"location_name"};
        try {
            open();
            cursor = db.query(dbhelper.LOCATION_TABLE_NAME,columns,null,null,null,null,null);

            if(cursor.getCount()>0)
            {
                int index = cursor.getColumnIndex("location_name");
                if (cursor.moveToFirst()) {
                    do {
                        list.add(cursor.getString(index));
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
            }
            else
            {
                Toast.makeText(context, "no locations", Toast.LENGTH_SHORT).show();
            }
            // closing connection

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        // returning locations
        return list;
    }

    public List<String> getNames(){
        List<String> list = new ArrayList<>();
        Cursor cursor;

        String[] columns = new String[]{"doctor_name"};
        try {
            open();
            cursor = db.query(dbhelper.DOCTOR_TABLE_NAME,columns,null,null,null,null,null);

            if(cursor.getCount()>0)
            {
                int index = cursor.getColumnIndex("doctor_name");
                if (cursor.moveToFirst()) {
                    do {
                        list.add(cursor.getString(index));
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db.close();
            }
            else
            {
                Toast.makeText(context, "no doctors found", Toast.LENGTH_SHORT).show();
            }
            // closing connection

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        // returning locations
        return list;
    }

    public String getDoctorSpeciality(String dname)
    {
        String speciality = "";
        String[] columns = {"speciality" };
        try {
            open();
            String selection = "doctor_name" + "=?";
            String[] selectionArgs = {dname};
            Cursor cursor = db.query(dbhelper.DOCTOR_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex("speciality");
                speciality = cursor.getString(index);
            }

            cursor.close();
            db.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return speciality;
    }
    
    public int getDoctorID(String Name)
    {
        int did = 0;
        try{
            open();
            Cursor c= db.rawQuery("SELECT D_ID FROM doctor WHERE doctor_name ='"+Name+"'",null);
            while (c.moveToNext()) {
                int index = c.getColumnIndex("D_ID");
                did = Integer.parseInt(c.getString(index));
            }
        }
        catch(Exception exc)
        {
            System.out.println(exc.getMessage());
        }
        return did;
    }

    public int getLocationID(String Name) {
        int lid = 0;
        try {
            open();
            Cursor c = db.rawQuery("SELECT L_ID FROM locations WHERE location_name ='" + Name+"'", null);
            while (c.moveToNext()) {
                int index = c.getColumnIndex("L_ID");
                lid = c.getInt(index);
            }
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
        return lid;
    }



    public Cursor getSchedule() {
        String dt = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String time = new SimpleDateFormat("hh:mm:ss").format(new Date());
        Cursor c = null;
        String sql = null;
        try {
            open();
            sql = "select doctor_schedule.S_ID as _id , doctor_schedule.date,doctor.doctor_name,doctor.speciality,locations.location_name,doctor_schedule.fromTime,doctor_schedule.toTime from doctor_schedule " +
                    "inner join doctor on doctor_schedule.d_id=doctor.D_ID inner join locations on doctor_schedule.l_id=locations.L_ID" +
                    " where doctor_schedule.date >= '"+dt+"'";

            c = db.rawQuery(sql, null);
            System.out.println(sql);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return c;
    }

    public Cursor getAppointments()
    {
        Cursor c = null;
        String sql = null;
        try{
            open();
            sql= "select appointment.A_ID as _id, appointment.image, doctor_schedule.date, patient.patient_name, doctor.doctor_name, locations.location_name from appointment " +
                    "inner join doctor_schedule on appointment.s_id = doctor_schedule.S_ID  " +
                    "inner join doctor on doctor_schedule.d_id = doctor.D_ID " +
                    "inner join patient on appointment.p_id = patient.P_ID " +
                    "inner join locations on doctor_schedule.l_id = locations.L_ID";

            c=db.rawQuery(sql,null);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            System.out.println(sql);
        }
        return c;
    }

    public Cursor getPatientList()
    {
        Cursor c = null;
        String sql= null;

        try
        {
            open();
            sql= "select P_ID as _id, patient_name, DOB from patient";
            c=db.rawQuery(sql,null);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public Cursor getDoctorsList()
    {
        Cursor c = null;
        String sql= null;

        try
        {
            open();
            sql= "select D_ID as _id, doctor_name, speciality from doctor";
            c=db.rawQuery(sql,null);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public Cursor getLocationsList()
    {
        Cursor c = null;
        String sql= null;

        try
        {
            open();
            sql= "select L_ID as _id, location_name, location_address from locations";
            c=db.rawQuery(sql,null);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return c;
    }

}
