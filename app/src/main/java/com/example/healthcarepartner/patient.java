package com.example.healthcarepartner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;




public class patient {

    DatabaseHelper dbhelper;
    SQLiteDatabase db;
    Context context;

    public static final String pid ="P_ID";
    public static final String pname ="patient_name";
    public static final String email="email";
    public static final String dob ="DOB";
    public static final String pwd ="password";

    public patient(Context c)
    {
        context=c;
    }

    public patient open() throws SQLException
    {
        dbhelper=new DatabaseHelper(context);
        db=dbhelper.getWritableDatabase();
        return this;
    }


    public long addUser(String name,String dob, String email, String password)
    {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("patient_name",name);
        contentValues.put("email",email);
        contentValues.put("DOB",dob);
        contentValues.put("password",password);
        long res = db.insert("patient",null,contentValues);
        db.close();
        return  res;
    }

    public Cursor patientLogin(String Email, String password)
    {
        Cursor c = null;
        String[] columns = { pid};
        open();
        String selection = email + "=?" + " and " + pwd + "=?";
        String[] selectionArgs = { Email, password };
        try{
            c = db.query(dbhelper.PATIENT_TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public long addAppointment(String reason,byte[] image,int pid,int sid)
    {
        long res = 0;
        try
        {
            open();
            ContentValues cv = new ContentValues();
            cv.put("reason",reason);
            cv.put("image",image);
            cv.put("p_id",pid);
            cv.put("s_id",sid);
            res = db.insert("appointment",null,cv);
            db.close();
        }
        catch(Exception exc)
        {
            System.out.println(exc.getMessage());
        }
        return res;
    }

    public String getPatientName(int id)
    {
        String name = null;
        try
        {
            open();
            Cursor c = db.rawQuery("select patient_name from patient where P_ID = " +id,null);
            if(c.moveToNext())
            {
                int index = c.getColumnIndex("patient_name");
                name=c.getString(index);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return name;
    }

    public Cursor getPatientAppointment(int pid) {
        Cursor c = null;
        String sql = null;
        try {
            open();
            sql = "select appointment.A_ID as _id, doctor_schedule.date, doctor.doctor_name, locations.location_name, doctor_schedule.fromTime from appointment " +
                    "inner join doctor_schedule on appointment.s_id=doctor_schedule.S_ID " +
                    "inner join locations on doctor_schedule.l_id = locations.L_ID" +
                    " inner join doctor on doctor_schedule.d_id = doctor.D_ID "+
                    " where appointment.p_id ="+pid +
                    " order by _id Asc";

            c = db.rawQuery(sql, null);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(sql);
        }
        return c;

    }

    public int deleteAppointment (int aid)
    {
        int result =0;
        try
        {
            open();
            result = db.delete("appointment","A_ID = "+aid,null);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return result;
    }


    public boolean changePassword (String Email,String password)
    {
        boolean isChanged = false;
        try
        {
            open();
            ContentValues cv = new ContentValues();
            cv.put(pwd,password);
            int result = db.update(dbhelper.PATIENT_TABLE_NAME,cv,email+"='"+Email+"'",null);
            if(result>0)
            {
                isChanged = true;
            }
            db.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return isChanged;
    }

    public boolean checkEmail(String email)
    {
        boolean exists = false;

        try
        {
            open();
            Cursor c = db.rawQuery("Select * from patient where email = '"+email+ "'",null);
            if(c.getCount()>0)
            {
                exists = true;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return exists;
    }



}
