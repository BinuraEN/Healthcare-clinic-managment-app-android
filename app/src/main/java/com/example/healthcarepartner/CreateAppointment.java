package com.example.healthcarepartner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateAppointment extends AppCompatActivity {

    patient p;

    int patient_ID;
    String ScheduleID;

    private static final int REQUEST_CODE = 100;

    String imageFilePath;

    EditText reason;
    ImageView appointmentImage;
    Button addAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        p = new patient(this);

        addAppointment = findViewById(R.id.btnAddAppointment);
        reason = findViewById(R.id.etxtReason);
        appointmentImage = findViewById(R.id.IVappointmentImage);

        Intent intent=getIntent();
        patient_ID= intent.getIntExtra("P_ID",0);
        ScheduleID = intent.getStringExtra("S_ID");


        appointmentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraIntent();
            }
        });

        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rsn = reason.getText().toString();
                int Schedule_id = Integer.parseInt(ScheduleID);

                Drawable d = appointmentImage.getDrawable();
                Bitmap btmp = ((BitmapDrawable)d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                btmp.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] bitmapdata = stream.toByteArray();

                if (rsn.isEmpty() || d==null )
                {
                    Toast.makeText(CreateAppointment.this, "please fill in all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    long result = p.addAppointment(rsn,bitmapdata,patient_ID,Schedule_id);
                    if(result>0)
                    {
                         Toast.makeText(CreateAppointment.this, "Appointment added Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                    Toast.makeText(CreateAppointment.this, "failed to add appointment", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void openCameraIntent()
    {
        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(camIntent.resolveActivity(getPackageManager()) != null)
        {
            File photoFile;
            try{
                photoFile = createImageFile();
            }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
                return;
            }

            Uri photoUri = FileProvider.getUriForFile(this,getPackageName()+".provider",photoFile);
            camIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
            startActivityForResult(camIntent,REQUEST_CODE);
        }
    }

    private File createImageFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        imageFilePath = image.getAbsolutePath();

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                appointmentImage.setImageURI(Uri.parse(imageFilePath));
            }
            else if(resultCode == RESULT_CANCELED)
            {
                appointmentImage.setImageResource(R.drawable.patient);
                Toast.makeText(this, "You Cancelled the operation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();


    }
}
