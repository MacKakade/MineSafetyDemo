package com.dmi.minesafety.demo.activity;

/**
 * Created by Mandar on 3/5/2015.
 */

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dmi.minesafety.demo.R;

import java.io.File;
import java.io.IOException;
import java.util.Date;


public class RecordAudioActivity extends ActionBarActivity {

    private static final String LOG_TAG = "AudioRecordTest";

    private Button mRecordButton = null;

    private MediaRecorder mRecorder = null;

    private MediaPlayer mPlayer = null;

    File file;

    boolean mStartRecording = true;

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            mRecordButton.setText("Start recording");
            Toast.makeText(RecordAudioActivity.this,
                    "Audio Note Saved at " + file.getPath()
                            + "\nClick to record again.",
                    Toast.LENGTH_LONG).show();
            stopRecording();
        }
    }

    private void startRecording() {

        File folder = new File(Environment.getExternalStorageDirectory() + "/msha");
        if (!folder.exists()) {
            folder.mkdir();
        }

        try {
            file = new File(folder, new Date() + "_audio.amr");
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(file.getPath());
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
            mRecorder.start();
            mRecordButton.setText("Stop recording");
            Toast.makeText(RecordAudioActivity.this,
                    "Recording Started...",
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this,
                    "Couldn't prepare recorder. Please check your settings",
                    Toast.LENGTH_LONG).show();
            finish();
        }


    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_record_audio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecordButton = (Button) findViewById(R.id.button_record_audio);
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
                mStartRecording = !mStartRecording;
            }
        });
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

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
