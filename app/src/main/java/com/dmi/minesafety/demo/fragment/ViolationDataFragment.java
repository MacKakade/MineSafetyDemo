package com.dmi.minesafety.demo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dmi.minesafety.demo.R;

import java.io.File;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that contain this fragment
 * must implement the {@link ViolationDataFragment} interface to handle interaction
 * events. Use the {@link ViolationDataFragment#newInstance} factory method to create
 * an instance of this fragment.
 */
public class ViolationDataFragment extends Fragment {

    private View view;
    public static final int LOAD_IMAGE_RESULTS = 1;

    private String[] mFileList;
    private File mPath = new File(Environment.getExternalStorageDirectory() + "//yourdir//");
    private String mChosenFile;
    private static final String FTYPE = ".txt";
    private static final int DIALOG_LOAD_FILE = 1000;

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
        view = inflater.inflate(R.layout.layout_citation_step_one, container, false);


        LayoutInflater lf = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        RelativeLayout chipView = (RelativeLayout) lf.inflate(R.layout.chipview_layout, null);
        chipView.setLayoutParams(new LayoutParams(400, LayoutParams.WRAP_CONTENT));
        TextView textView = (TextView) chipView.findViewById(R.id.txv_chip_label);
        textView.setText("audio file name");

        final EditText edtAttachment = (EditText) view.findViewById(R.id.edt_attachment);
        edtAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(getActivity(), edtAttachment);
                popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //create the intent for ImageGallery
                        Intent i = new Intent(Intent.ACTION_PICK);
                        i.setType("image/*");
                        //Start new activity with the LOAD_IMAGE_RESULT to handle back the result when the image is picked from the Image Gallery
                        startActivityForResult(i, LOAD_IMAGE_RESULTS);
                        return true;
                    }
                });

                popup.show();//showing popup menu

            }
        });

        ImageButton delete = (ImageButton) chipView.findViewById(R.id.btn_delete);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_attachment);
        linearLayout.addView(chipView);

        return view;
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

}
