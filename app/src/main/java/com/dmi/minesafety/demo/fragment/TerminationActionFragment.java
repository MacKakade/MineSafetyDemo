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
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the {@link com.dmi.minesafety.demo.fragment.TerminationActionFragment}
 * interface to handle interaction events. Use the {@link
 * com.dmi.minesafety.demo.fragment.TerminationActionFragment#newInstance}
 * factory method to create an instance of this fragment.
 */
public class TerminationActionFragment extends Fragment {

    public static TerminationActionFragment newInstance() {
        TerminationActionFragment fragment = new TerminationActionFragment();
        return fragment;
    }

    public TerminationActionFragment() {
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
        return inflater
                .inflate(R.layout.layout_citation_step_three, container, false);
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
