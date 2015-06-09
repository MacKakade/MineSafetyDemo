package com.dmi.mobile.inspection.activity;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dmi.mobile.inspection.R;
import com.dmi.mobile.inspection.greendao.Citation;
import com.dmi.mobile.inspection.repository.CitationRepository;

/**
 * Created by Digvijay on 2/23/2015.
 */
public class CapturePhotoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Uri uri = getIntent().getExtras().getParcelable("img_uri");
        ImageView photo = (ImageView) findViewById(R.id.imv_camera_capture);
//        String[] mLocationsTitles = getResources()
//                .getStringArray(R.array.spinner_data);
//        Spinner mLocationSpinner = (Spinner) findViewById(
//                R.id.spinner_location);
//        mLocationSpinner.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.layout_spinner_item_drawer_black, mLocationsTitles));

        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;

            AssetFileDescriptor fileDescriptor = null;
            fileDescriptor = getContentResolver()
                    .openAssetFileDescriptor(uri, "r");

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
                int count = CitationRepository
                        .getAllCitations(CapturePhotoActivity.this).size();
                count++;
                Citation citation = new Citation((long) count, uri.toString());
                CitationRepository
                        .insertOrUpdate(CapturePhotoActivity.this, citation);
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
        inflater.inflate(R.menu.make_no_items, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
