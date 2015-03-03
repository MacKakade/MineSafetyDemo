package com.dmi.minesafety.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * An activity representing a list of Documents. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a {@link
 * com.dmi.minesafety.demo.DocumentDetailActivity} representing item details. On
 * tablets, the activity presents the list of items and item details
 * side-by-side using two vertical panes. <p> The activity makes heavy use of
 * fragments. The list of items is a {@link com.dmi.minesafety.demo.DocumentListFragment}
 * and the item details (if present) is a {@link com.dmi.minesafety.demo.DocumentDetailFragment}.
 * <p> This activity also implements the required {@link
 * com.dmi.minesafety.demo.DocumentListFragment.Callbacks} interface to listen
 * for item selections.
 */
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
     * Callback method from {@link com.dmi.minesafety.demo.DocumentListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */

}
