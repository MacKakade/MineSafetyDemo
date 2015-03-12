package com.dmi.minesafety.demo.fragment;

import com.dmi.minesafety.demo.R;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that contain this fragment
 * must implement the {@link com.dmi.minesafety.demo.fragment.VoilationDataFragment} interface to handle interaction
 * events. Use the {@link com.dmi.minesafety.demo.fragment.VoilationDataFragment#newInstance} factory method to create
 * an instance of this fragment.
 */
public class VoilationDataFragment extends Fragment {


    public static VoilationDataFragment newInstance() {
        VoilationDataFragment fragment = new VoilationDataFragment();
        return fragment;
    }

    public VoilationDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_citation_step_one, container, false);
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
