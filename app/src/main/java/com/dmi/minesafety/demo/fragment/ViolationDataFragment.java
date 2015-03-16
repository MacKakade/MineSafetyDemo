package com.dmi.minesafety.demo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dmi.minesafety.demo.R;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that contain this fragment
 * must implement the {@link ViolationDataFragment} interface to handle interaction
 * events. Use the {@link ViolationDataFragment#newInstance} factory method to create
 * an instance of this fragment.
 */
public class ViolationDataFragment extends Fragment {

    private final int LOAD_FILE_RESULTS = 1;
    private final int LOAD_IMAGE_RESULTS = 2;
    private final int LOAD_VIDEO_RESULTS = 3;
    private final int LOAD_AUDIO_RESULTS = 4;
    private LinearLayout attachmentsContainer;

    public static ViolationDataFragment newInstance() {
        ViolationDataFragment fragment = new ViolationDataFragment();
        return fragment;
    }

    public ViolationDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_citation_step_one, container, false);
        attachmentsContainer = (LinearLayout) view.findViewById(R.id.linear_attachment);
        checkIfHintViewRemoved();

        final ImageButton btnAttachFile = (ImageButton) view.findViewById(R.id.btn_attachment);
        btnAttachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(getActivity(), btnAttachFile);
                popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent intent;
                        switch (item.getItemId()) {

                            case R.id.file:
//                                intent = new Intent(Intent.ACTION_PICK);
//                                intent.setType("file/*");
//                                startActivityForResult(intent, LOAD_FILE_RESULTS);
                                break;

                            case R.id.picture:
                                intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(intent, LOAD_IMAGE_RESULTS);
                                break;

                            case R.id.video:
                                intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("video/*");
                                startActivityForResult(intent, LOAD_VIDEO_RESULTS);
                                break;

                            case R.id.audio:
                                intent = new Intent(Intent.ACTION_PICK);
                                intent.setData(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, LOAD_AUDIO_RESULTS);
                                break;
                        }

                        return true;
                    }
                });

                popup.show();//showing popup menu

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {

        LayoutInflater lf = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout chipView;
        TextView fileName;
        FileMetaData fileMetaData;
        ImageButton delete;
        ImageView fileType;
        RelativeLayout.LayoutParams layoutParams;

        if (resultCode == Activity.RESULT_OK) {

            checkIfHintViewRemoved();

            switch (requestCode) {

                case LOAD_FILE_RESULTS:
                    chipView = (RelativeLayout) lf.inflate(R.layout.layout_chipview, null);
                    layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(10, 10, 10, 10);
                    chipView.setLayoutParams(layoutParams);
                    fileName = (TextView) chipView.findViewById(R.id.txv_file_name);
                    fileMetaData = getFileMetaData(resultIntent.getData(), false);
                    fileName.setText(fileMetaData.getName());
                    delete = (ImageButton) chipView.findViewById(R.id.btn_delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            attachmentsContainer.removeView(chipView);
                            checkIfHintViewRemoved();
                        }
                    });
                    attachmentsContainer.addView(chipView);
                    break;

                case LOAD_AUDIO_RESULTS:
                    chipView = (RelativeLayout) lf.inflate(R.layout.layout_chipview, null);
                    layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(10, 10, 10, 10);
                    chipView.setLayoutParams(layoutParams);
                    fileName = (TextView) chipView.findViewById(R.id.txv_file_name);
                    fileMetaData = getFileMetaData(resultIntent.getData(), false);
                    fileName.setText(fileMetaData.getName());
                    fileType = (ImageView) chipView.findViewById(R.id.imv_file_type);
                    fileType.setImageDrawable(getResources().getDrawable(R.drawable.ic_audio_popup));
                    delete = (ImageButton) chipView.findViewById(R.id.btn_delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            attachmentsContainer.removeView(chipView);
                            checkIfHintViewRemoved();
                        }
                    });
                    attachmentsContainer.addView(chipView);
                    break;

                case LOAD_IMAGE_RESULTS:
                    chipView = (RelativeLayout) lf.inflate(R.layout.layout_chipview_picture, null);
                    layoutParams = new RelativeLayout.LayoutParams(300, 300);
                    layoutParams.setMargins(10, 10, 10, 10);
                    chipView.setLayoutParams(layoutParams);
                    fileName = (TextView) chipView.findViewById(R.id.txv_file_name);
                    TextView fileSize = (TextView) chipView.findViewById(R.id.txv_file_size);
                    ImageView thumbnail = (ImageView) chipView.findViewById(R.id.imv_thumbnail);
                    fileMetaData = getFileMetaData(resultIntent.getData(), true);
                    fileName.setText(fileMetaData.getName());
                    fileSize.setText(fileMetaData.getSize() + " MB");
                    thumbnail.setImageBitmap(fileMetaData.getThumbnail());
                    thumbnail.setScaleType(ImageView.ScaleType.FIT_XY);

                    delete = (ImageButton) chipView.findViewById(R.id.btn_delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            attachmentsContainer.removeView(chipView);
                            checkIfHintViewRemoved();
                        }
                    });
                    attachmentsContainer.addView(chipView);
                    break;

                case LOAD_VIDEO_RESULTS:
                    chipView = (RelativeLayout) lf.inflate(R.layout.layout_chipview, null);
                    layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(10, 10, 10, 10);
                    chipView.setLayoutParams(layoutParams);
                    fileName = (TextView) chipView.findViewById(R.id.txv_file_name);
                    fileMetaData = getFileMetaData(resultIntent.getData(), false);
                    fileName.setText(fileMetaData.getName());
                    fileType = (ImageView) chipView.findViewById(R.id.imv_file_type);
                    fileType.setImageDrawable(getResources().getDrawable(R.drawable.ic_video_popup));
                    delete = (ImageButton) chipView.findViewById(R.id.btn_delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            attachmentsContainer.removeView(chipView);
                            checkIfHintViewRemoved();
                        }
                    });
                    attachmentsContainer.addView(chipView);
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, resultIntent);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private FileMetaData getFileMetaData(Uri uri, boolean isPictureFile) {

        // The query, since it only applies to a single document, will only return
        // one row. There's no need to filter, sort, or select fields, since we want
        // all fields for one document.
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null, null);
        FileMetaData fileMetaData = null;
        Bitmap bitmap = null;

        if (isPictureFile) {
            try {
                bitmap = getBitmapFromUri(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {

                // Note it's called "Display Name".  This is
                // provider-specific, and might not necessarily be the file name.
                String displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                Log.i("TAG", "Display Name: " + displayName);

                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                // If the size is unknown, the value stored is null.  But since an
                // int can't be null in Java, the behavior is implementation-specific,
                // which is just a fancy term for "unpredictable".  So as
                // a rule, check if it's null before assigning to an int.  This will
                // happen often:  The storage API allows for remote files, whose
                // size might not be locally known.
                String size = null;
                if (!cursor.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    float sizeLong = bytesToMeg(Float.parseFloat(cursor.getString(sizeIndex)));
                    size = String.valueOf(sizeLong);
                } else {
                    size = "Unknown";
                }
                Log.i("TAG", "Size: " + size);

                fileMetaData = new FileMetaData(displayName, size, bitmap);
            }


        } finally {
            cursor.close();
        }
        return fileMetaData;
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


    private float bytesToMeg(float bytes) {
        float MEGABYTE = 1024L * 1024F;
        return bytes / MEGABYTE;
    }

    private class FileMetaData {

        String name;
        String size;
        Bitmap thumbnail;

        private FileMetaData(String name, String size, Bitmap thumbnail) {
            this.name = name;
            this.size = size;
            this.thumbnail = thumbnail;
        }

        public String getName() {
            return name;
        }

        public String getSize() {
            return size;
        }

        public Bitmap getThumbnail() {
            return thumbnail;
        }
    }

    private void checkIfHintViewRemoved() {

        if (attachmentsContainer.getChildCount() == 0) {
            TextView textView = new TextView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 5, 5, 5);
            textView.setLayoutParams(layoutParams);
            textView.setHint("Attach a file..");
            textView.setTag("hint");
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            attachmentsContainer.addView(textView);
        } else if (attachmentsContainer.findViewWithTag("hint") != null) {
            attachmentsContainer.removeViewAt(0);
        }
    }
}
