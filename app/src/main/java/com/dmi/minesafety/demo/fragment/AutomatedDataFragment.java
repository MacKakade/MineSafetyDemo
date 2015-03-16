package com.dmi.minesafety.demo.fragment;

import com.dmi.minesafety.demo.R;
import com.github.gcacace.signaturepad.views.SignaturePad;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link AutomatedDataFragment} interface to handle
 * interaction events. Use the {@link AutomatedDataFragment#newInstance} factory
 * method to create an instance of this fragment.
 */
public class AutomatedDataFragment extends Fragment {

    View view;

    ImageView signature1;

    ImageView signature2;

    static ActionBarActivity mActivity;


    static ImageView currentSignatureView;

    public static AutomatedDataFragment newInstance() {
        AutomatedDataFragment fragment = new AutomatedDataFragment();
        return fragment;
    }

    public AutomatedDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater
                .inflate(R.layout.layout_citation_step_four, container, false);
        signature1 = (ImageView) view.findViewById(R.id.signature1);
        signature2 = (ImageView) view.findViewById(R.id.signature2);
        mActivity = (ActionBarActivity) getActivity();
        signature1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSignatureView = signature1;
                SignatureDialogFragment dFragment
                        = SignatureDialogFragment.newInstance();
// Show DialogFragment
                dFragment.show(getActivity().getSupportFragmentManager(),
                        "Dialog Fragment");
            }
        });

        signature2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSignatureView = signature2;
                SignatureDialogFragment dFragment
                        = SignatureDialogFragment.newInstance();
// Show DialogFragment
                dFragment.show(getActivity().getSupportFragmentManager(),
                        "Dialog Fragment");
            }
        });

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


    public static class SignatureDialogFragment extends DialogFragment {

        private SignaturePad mSignaturePad;

        private Button mClearButton;

        private Button mSaveButton;


        public static SignatureDialogFragment newInstance() {
            return new SignatureDialogFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater
                    .inflate(R.layout.dialog_signature, container,
                            false);

            mSignaturePad = (SignaturePad) rootView.findViewById(
                    R.id.signature_pad);
            mSignaturePad
                    .setOnSignedListener(new SignaturePad.OnSignedListener() {
                        @Override
                        public void onSigned() {
                            mSaveButton.setEnabled(true);
                            mClearButton.setEnabled(true);
                        }

                        @Override
                        public void onClear() {
                            mSaveButton.setEnabled(false);
                            mClearButton.setEnabled(false);
                        }
                    });
            mClearButton = (Button) rootView.findViewById(R.id.clear_button);
            mSaveButton = (Button) rootView.findViewById(R.id.save_button);
            mClearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSignaturePad.clear();
                }
            });
            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                    signatureBitmap = Bitmap
                            .createScaledBitmap(signatureBitmap, 100, 100,
                                    true);
                    if (addSignatureToGallery(signatureBitmap)) {
                        Toast.makeText(getActivity(),
                                "Signature saved into the Gallery",
                                Toast.LENGTH_SHORT).show();

                        currentSignatureView.setImageBitmap(signatureBitmap);

                        dismiss();

                    } else {
                        Toast.makeText(getActivity(),
                                "Unable to store the signature",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            });

            return rootView;
        }
    }

    public static File getAlbumStorageDir(String albumName) {
// Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public static void saveBitmapToJPG(Bitmap bitmap, File photo)
            throws IOException {
        Bitmap newBitmap = Bitmap
                .createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                        Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public static boolean addSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"),
                    String.format("Signature_%d.jpg",
                            System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(photo);
            mediaScanIntent.setData(contentUri);
            mActivity.sendBroadcast(mediaScanIntent);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
