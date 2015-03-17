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
 * contain this fragment must implement the {@link com.dmi.minesafety.demo.fragment.InspectorEvaluation2Fragment}
 * interface to handle interaction events. Use the {@link
 * com.dmi.minesafety.demo.fragment.InspectorEvaluation2Fragment#newInstance} factory method to create an instance
 * of this fragment.
 */
public class InspectorEvaluation2Fragment extends Fragment {

    View view;

    public static InspectorEvaluation2Fragment newInstance() {
        InspectorEvaluation2Fragment fragment
                = new InspectorEvaluation2Fragment();

        return fragment;
    }

    public InspectorEvaluation2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_citation_step_two_2, container, false);
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
