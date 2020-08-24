package com.example.healthcarepartner;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ImageCursorAdapter_admin extends SimpleCursorAdapter {

    private Cursor c;
    private Context context;

    public ImageCursorAdapter_admin(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.c = c;
        this.context =context;
    }

    @Override
    public View getView(int position, View inView, ViewGroup parent) {
        View v = inView;
        if(v==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.appointment_admin_single_list_item,null);
        }
        this.c.moveToPosition(position);
        byte[] image = c.getBlob(this.c.getColumnIndex("image"));
        String date = c.getString(this.c.getColumnIndex("date"));
        String pName = c.getString(this.c.getColumnIndex("patient_name"));
        String dName = c.getString(this.c.getColumnIndex("doctor_name"));
        String lName = c.getString(this.c.getColumnIndex("location_name"));
        ImageView iv = v.findViewById(R.id.ivApImg);
        if(image != null);
        {
            if(image.length>0)
            {
                iv.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
            }
            else
            {
                iv.setImageResource(R.drawable.patient);
            }
        }
        TextView apDate = v.findViewById(R.id.txtAppointmentDate);
        apDate.setText(date);

        TextView patient = v.findViewById(R.id.txtPatientID);
        patient.setText(pName);

        TextView doc = v.findViewById(R.id.txtDocName);
        doc.setText(dName);

        TextView Location = v.findViewById(R.id.txtLoc);
        Location.setText(lName);

        return (v);
    }
}
