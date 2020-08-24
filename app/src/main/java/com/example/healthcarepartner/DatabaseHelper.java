package com.example.healthcarepartner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="healthcarePartner.db";
    public static final String PATIENT_TABLE_NAME ="patient";
    public static final String LOCATION_TABLE_NAME = "locations";
    public static final String DOCTOR_TABLE_NAME = "doctor";
    public static final String DOCTOR_SCHEDULE_TABLE_NAME = "doctor_schedule";
    public static final String APPOINTMENT_TABLE_NAME = "appointment";




    public DatabaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null,1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + LOCATION_TABLE_NAME + "(L_ID INTEGER PRIMARY KEY AUTOINCREMENT,location_name TEXT,location_address TEXT)");

        db.execSQL(" CREATE TABLE " + PATIENT_TABLE_NAME + "(P_ID INTEGER PRIMARY KEY AUTOINCREMENT , patient_name TEXT , email TEXT, DOB DATE , password TEXT)");

        db.execSQL("CREATE TABLE " + DOCTOR_TABLE_NAME + "(D_ID INTEGER PRIMARY KEY AUTOINCREMENT,doctor_name TEXT,speciality TEXT)");

        db.execSQL("CREATE TABLE " + DOCTOR_SCHEDULE_TABLE_NAME + "(S_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date DATE, d_id INTEGER ,speciality TEXT,l_id INTEGER ,fromTime TIME,toTime TIME, " +
                "FOREIGN KEY (d_id) REFERENCES "+DOCTOR_TABLE_NAME+"(D_ID),FOREIGN KEY (l_id) REFERENCES "+LOCATION_TABLE_NAME+"(L_ID))");

        db.execSQL("CREATE TABLE " +APPOINTMENT_TABLE_NAME+ "(A_ID INTEGER PRIMARY KEY AUTOINCREMENT,reason TEXT,image BLOB,p_id INTEGER,s_id INTEGER, " +
                "FOREIGN KEY (p_id) REFERENCES "+ PATIENT_TABLE_NAME + "(P_ID),FOREIGN KEY (s_id) REFERENCES "+ DOCTOR_SCHEDULE_TABLE_NAME + "(S_ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + PATIENT_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + LOCATION_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + DOCTOR_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + DOCTOR_SCHEDULE_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + APPOINTMENT_TABLE_NAME);
        onCreate(db);
    }
}
