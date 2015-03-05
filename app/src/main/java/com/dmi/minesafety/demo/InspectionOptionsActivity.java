package com.dmi.minesafety.demo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;


public class InspectionOptionsActivity extends ActionBarActivity {

    private Uri mUri;

//    private Spinner mLocationSpinner;
//
//    private String[] mLocationsTitles;

     AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_options);



//        Button b = (Button) findViewById(R.id.btn_record_location);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(InspectionOptionsActivity.this,
//                        MineMapActivity.class));
//                finish();
//            }
//        });

//        mLocationsTitles = getResources().getStringArray(R.array.spinner_data);
//
//        mLocationSpinner = (Spinner) findViewById(R.id.spinner_location);
//
//        mLocationSpinner.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.layout_spinner_item_drawer_black, mLocationsTitles));

        RelativeLayout btnPicture = (RelativeLayout) findViewById(
                R.id.rel_take_picture);
        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    takePicture();
                } catch (Exception e) {
                    Toast.makeText(InspectionOptionsActivity.this,
                            "Unable to take picture..Please check your camera settings.",
                            Toast.LENGTH_LONG);
                }

            }
        });

        RelativeLayout btnBarcode = (RelativeLayout) findViewById(
                R.id.rel_scan_barcode);
        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    takePicture();
                } catch (Exception e) {
                    Toast.makeText(InspectionOptionsActivity.this,
                            "Unable to take picture..Please check your camera settings.",
                            Toast.LENGTH_LONG);
                }

            }
        });

        RelativeLayout btnNote = (RelativeLayout) findViewById(
                R.id.rel_take_notes);
        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(InspectionOptionsActivity.this,
                        MakeNoteActivity.class));
            }
        });

        RelativeLayout btnAllForms = (RelativeLayout) findViewById(
                R.id.rel_all_forms);

        btnAllForms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        InspectionOptionsActivity.this);

                alertDialog = alert.create();

                View view = getLayoutInflater()
                        .inflate(R.layout.layout_all_forms, null, false);
                view.findViewById(R.id.close)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });

                alertDialog.setView(view);
                alertDialog.show();
            }
        });

        Button buttonArtifacts = (Button) findViewById(R.id.btn_my_artifacts);
        buttonArtifacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        InspectionOptionsActivity.this);

                alertDialog = alert.create();

                View view = getLayoutInflater()
                        .inflate(R.layout.layout_inspection_artifacts, null, false);
                view.findViewById(R.id.close)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });

                alertDialog.setView(view);
                alertDialog.show();
            }
        });
    }

    private void takePicture() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,
                "IMG_" + new Date() + ".jpg");

        mUri = getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values); // store content values

        Intent takePhotoIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                mUri);
        startActivityForResult(takePhotoIntent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {

        if (resultCode != RESULT_CANCELED) {
            if (requestCode == 0) {
                Intent intent = new Intent(this, CapturePhotoActivity.class);
                intent.putExtra("img_uri", mUri);
                startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
//            startActivity(new Intent(InspectionOptionsActivity.this,
//                    MainActivity.class));
//            finish();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
