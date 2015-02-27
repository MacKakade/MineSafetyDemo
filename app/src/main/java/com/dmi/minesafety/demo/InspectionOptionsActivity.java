package com.dmi.minesafety.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Date;


public class InspectionOptionsActivity extends Activity {

    private Uri mUri;

    private Spinner mLocationSpinner;

    private String[] mLocationsTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_options);

        Button b = (Button) findViewById(R.id.btn_record_location);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InspectionOptionsActivity.this,
                        MineMapActivity.class));
                finish();
            }
        });

        mLocationsTitles = getResources().getStringArray(R.array.spinner_data);

        mLocationSpinner = (Spinner) findViewById(R.id.spinner_location);

        mLocationSpinner.setAdapter(new ArrayAdapter<String>(this,
                R.layout.layout_spinner_item_drawer_black, mLocationsTitles));

        Button btnPicture = (Button) findViewById(R.id.button_take_picture);
        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        Button btnNote = (Button) findViewById(R.id.button_make_notes);
        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(InspectionOptionsActivity.this,
                        MakeNoteActivity.class));
            }
        });


        Button btnArtifacts = (Button) findViewById(R.id.button_my_artifacts);
        btnArtifacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        InspectionOptionsActivity.this);
                alert.setView(getLayoutInflater().inflate(R.layout.layout_inspection_artifacts, null, false));
                alert.show();
            }
        });


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

        if (item.getItemId() == R.id.menu_go_to_map) {
            startActivity(new Intent(InspectionOptionsActivity.this,
                    MainActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
