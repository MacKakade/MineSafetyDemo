package com.dmi.mobile.inspection.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmi.mobile.inspection.R;

public class DocumentListParentFragment extends Fragment {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: If exposing deep links into your app, handle intents here.
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View v = inflater
                .inflate(R.layout.activity_document_list, container, false);

        if (v.findViewById(R.id.document_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
//            ((DocumentListFragment) getActivity().getSupportFragmentManager()
//                    .findFragmentById(R.id.document_list))
//                    .setActivateOnItemClick(true);
        }

        return v;
    }

    /**
     * Callback method from {@link com.dmi.minesafety.demo.fragment.DocumentListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */

}
