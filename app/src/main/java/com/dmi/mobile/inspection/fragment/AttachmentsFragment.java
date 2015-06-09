package com.dmi.mobile.inspection.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dmi.mobile.inspection.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the {@link AttachmentsFragment}
 * interface to handle interaction events. Use the {@link
 * AttachmentsFragment#newInstance} factory method to create an instance of this
 * fragment.
 */

public class AttachmentsFragment extends Fragment {

    private final int LOAD_FILE_RESULTS = 1;

    private final int LOAD_IMAGE_RESULTS = 2;

    private final int LOAD_VIDEO_RESULTS = 3;

    private final int LOAD_AUDIO_RESULTS = 4;

    private LinearLayout attachmentsContainer;

    private List<LinearLayout> pictureContainers = new ArrayList<>();

    private int maxPicturesCount;

    public static AttachmentsFragment newInstance() {
        AttachmentsFragment fragment
                = new AttachmentsFragment();

        return fragment;
    }

    public AttachmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.layout_citation_step_five, container, false);
        attachmentsContainer = (LinearLayout) view
                .findViewById(R.id.linear_attachment);
        checkIfHintViewRemoved();

        setCountMaxPictures();

        final ImageButton btnAttachFile = (ImageButton) view
                .findViewById(R.id.btn_attachment);
        btnAttachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(getActivity(), btnAttachFile);
                popup.getMenuInflater()
                        .inflate(R.menu.menu_popup, popup.getMenu());
                popup.setOnMenuItemClickListener(
                        new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {

                                Intent intent;
                                switch (item.getItemId()) {

//                                    case R.id.file:
//                                intent = new Intent(Intent.ACTION_PICK);
//                                intent.setType("file/*");
//                                startActivityForResult(intent, LOAD_FILE_RESULTS);
//                                        break;

                                    case R.id.picture:
                                        intent = new Intent(Intent.ACTION_PICK);
                                        intent.setType("image/*");
                                        startActivityForResult(intent,
                                                LOAD_IMAGE_RESULTS);
                                        break;

                                    case R.id.video:
                                        intent = new Intent(Intent.ACTION_PICK);
                                        intent.setType("video/*");
                                        startActivityForResult(intent,
                                                LOAD_VIDEO_RESULTS);

                                        break;

                                    case R.id.audio:
                                        intent = new Intent(Intent.ACTION_PICK);
                                        intent.setData(
                                                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(intent,
                                                LOAD_AUDIO_RESULTS);
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
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultIntent) {

        LayoutInflater lf = (LayoutInflater) getActivity()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout chipView;
        TextView fileName;
        FileMetaData fileMetaData;
        ImageButton delete;
        ImageView fileType;
        LinearLayout.LayoutParams layoutParams;

        if (resultCode == Activity.RESULT_OK) {

            checkIfHintViewRemoved();

            switch (requestCode) {

                case LOAD_FILE_RESULTS:
                    chipView = (RelativeLayout) lf
                            .inflate(R.layout.layout_chipview, null);
                    layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(10, 10, 10, 10);
                    chipView.setLayoutParams(layoutParams);
                    fileName = (TextView) chipView
                            .findViewById(R.id.txv_file_name);
                    fileMetaData = getFileMetaData(resultIntent.getData(),
                            false);
                    fileName.setText(fileMetaData.getName());
                    delete = (ImageButton) chipView
                            .findViewById(R.id.btn_delete);
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
                    chipView = (RelativeLayout) lf
                            .inflate(R.layout.layout_chipview, null);
                    layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(10, 10, 10, 10);
                    chipView.setLayoutParams(layoutParams);
                    fileName = (TextView) chipView
                            .findViewById(R.id.txv_file_name);
                    fileMetaData = getFileMetaData(resultIntent.getData(),
                            false);
                    fileName.setText(fileMetaData.getName());
                    fileType = (ImageView) chipView
                            .findViewById(R.id.imv_file_type);
                    fileType.setImageDrawable(getResources()
                            .getDrawable(R.drawable.ic_audio_popup));
                    delete = (ImageButton) chipView
                            .findViewById(R.id.btn_delete);
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
                    chipView = (RelativeLayout) lf
                            .inflate(R.layout.layout_chipview_picture, null);
                    layoutParams = new LinearLayout.LayoutParams(300, 300);
                    layoutParams.setMargins(10, 10, 10, 10);
                    chipView.setLayoutParams(layoutParams);

                    fileName = (TextView) chipView
                            .findViewById(R.id.txv_file_name);
                    TextView fileSize = (TextView) chipView
                            .findViewById(R.id.txv_file_size);
                    ImageView thumbnail = (ImageView) chipView
                            .findViewById(R.id.imv_thumbnail);
                    fileMetaData = getFileMetaData(resultIntent.getData(),
                            true);
                    fileName.setText(fileMetaData.getName());
                    fileSize.setText(fileMetaData.getSize() + " MB");
                    thumbnail.setImageBitmap(fileMetaData.getThumbnail());
                    thumbnail.setScaleType(ImageView.ScaleType.FIT_XY);

                    delete = (ImageButton) chipView
                            .findViewById(R.id.btn_delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            for (int i = 0; i < pictureContainers.size(); i++) {

                                LinearLayout linearLayout = pictureContainers.get(i);
                                if (chipView.getParent() == linearLayout) {
                                    linearLayout.removeView(chipView);
                                    if (linearLayout.getChildCount() == 0) {
                                        pictureContainers.remove(linearLayout);
                                    }

                                    if (i + 1 < pictureContainers.size()) {
                                        LinearLayout linearLayout1 = pictureContainers.get(i + 1);
                                        View child = linearLayout1.getChildAt(0);
                                        linearLayout1.removeViewAt(0);
                                        linearLayout.addView(child);
                                        if (linearLayout1.getChildCount() == 0) {
                                            pictureContainers.remove(linearLayout1);
                                        }
                                    }
                                }
                            }
                            checkIfHintViewRemoved();
                        }
                    });

                    LinearLayout linearLayout = null;
                    if (pictureContainers.size() > 0) {
                        linearLayout = pictureContainers.get(pictureContainers.size() - 1);

                        if (linearLayout.getChildCount() < maxPicturesCount) {
                            linearLayout.addView(chipView);
                        } else {
                            linearLayout = new LinearLayout(getActivity());
                            layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            linearLayout.setLayoutParams(layoutParams);
                            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            linearLayout.addView(chipView);

                            int index = attachmentsContainer.indexOfChild(pictureContainers.get(pictureContainers.size() - 1));
                            attachmentsContainer.addView(linearLayout, index + 1);
                            pictureContainers.add(linearLayout);
                        }

                    } else {
                        linearLayout = new LinearLayout(getActivity());
                        layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        linearLayout.setLayoutParams(layoutParams);
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout.addView(chipView);

                        attachmentsContainer.addView(linearLayout);
                        pictureContainers.add(linearLayout);
                    }
                    break;

                case LOAD_VIDEO_RESULTS:
                    chipView = (RelativeLayout) lf
                            .inflate(R.layout.layout_chipview, null);
                    layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(10, 10, 10, 10);
                    chipView.setLayoutParams(layoutParams);
                    fileName = (TextView) chipView
                            .findViewById(R.id.txv_file_name);
                    fileMetaData = getFileMetaData(resultIntent.getData(),
                            false);
                    fileName.setText(fileMetaData.getName());
                    fileType = (ImageView) chipView
                            .findViewById(R.id.imv_file_type);
                    fileType.setImageDrawable(getResources()
                            .getDrawable(R.drawable.ic_video_popup));
                    delete = (ImageButton) chipView
                            .findViewById(R.id.btn_delete);
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
        Cursor cursor = getActivity().getContentResolver()
                .query(uri, null, null, null, null, null);
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
                    float sizeLong = bytesToMeg(
                            Float.parseFloat(cursor.getString(sizeIndex)));
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
        Bitmap actuallyUsableBitmap = null;
        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;

            AssetFileDescriptor fileDescriptor = null;
            fileDescriptor = getActivity().getContentResolver()
                    .openAssetFileDescriptor(uri, "r");

            actuallyUsableBitmap
                    = BitmapFactory.decodeFileDescriptor(
                    fileDescriptor.getFileDescriptor(), null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return actuallyUsableBitmap;
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
            LinearLayout.LayoutParams layoutParams
                    = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 5, 5, 5);
            textView.setLayoutParams(layoutParams);
            textView.setPadding(5, 5, 5, 5);
            textView.setHint("Attachments..");
            textView.setTag("hint");
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            attachmentsContainer.addView(textView);
        } else if (attachmentsContainer.findViewWithTag("hint") != null) {
            attachmentsContainer.removeViewAt(0);
        }
    }

    private void setCountMaxPictures() {

        ViewTreeObserver vto = attachmentsContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                attachmentsContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width = attachmentsContainer.getMeasuredWidth();
                maxPicturesCount = width / 320;
                Log.i("width", "" + width);
                Log.i("count", "" + maxPicturesCount);
            }
        });

    }
}
