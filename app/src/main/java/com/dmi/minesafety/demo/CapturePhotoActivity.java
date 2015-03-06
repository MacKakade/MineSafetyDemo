package com.dmi.minesafety.demo;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Digvijay on 2/23/2015.
 */
public class CapturePhotoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Uri uri = getIntent().getExtras().getParcelable("img_uri");
        ImageView photo = (ImageView) findViewById(R.id.imv_camera_capture);
        String[] mLocationsTitles = getResources().getStringArray(R.array.spinner_data);
        Spinner mLocationSpinner = (Spinner) findViewById(R.id.spinner_location);
        mLocationSpinner.setAdapter(new ArrayAdapter<String>(this,
                R.layout.layout_spinner_item_drawer_black, mLocationsTitles));

        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;

            AssetFileDescriptor fileDescriptor =null;
            fileDescriptor = getContentResolver().openAssetFileDescriptor(uri, "r");

            Bitmap actuallyUsableBitmap
                    = BitmapFactory.decodeFileDescriptor(
                    fileDescriptor.getFileDescriptor(), null, options);
            photo.setImageBitmap(actuallyUsableBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        ImageView btnSave = (ImageView) findViewById(R.id.btn_save);
        ImageView btnCancel = (ImageView) findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CapturePhotoActivity.this,
                        "   Photo is saved!   ", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inspection_options, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        if (item.getItemId() == R.id.menu_go_to_map) {
//            startActivity(new Intent(CapturePhotoActivity.this,
//                    MainActivity.class));
//            finish();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
