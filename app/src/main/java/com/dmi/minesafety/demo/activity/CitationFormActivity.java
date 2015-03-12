package com.dmi.minesafety.demo.activity;

import com.dmi.minesafety.demo.R;
import com.dmi.minesafety.demo.fragment.CitationFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class CitationFormActivity extends ActionBarActivity {


    ViewPager mViewPager;

    ImageView mBtnPrevious;

    ImageView mBtnNext;

    TextView textViewStepNumber;

    int mPosition=0;

    public String[] mArray = new String[]{"Section I - Violation Data",
            "Section II - Inspector's Evaluation",
            "Section III- Termination Action",
            "Section IV - Automated System Data"};

    private CitationPagerAdaptor mCitationPagerAdaptor;

    public int[] mArrayCitationLayout = new int[]{
            R.layout.layout_citation_step_one,
            R.layout.layout_citation_step_two,
            R.layout.layout_citation_step_three,
            R.layout.layout_citation_step_four};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citation);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mBtnPrevious = (ImageView) findViewById(R.id.previous_button);
        mBtnNext = (ImageView) findViewById(R.id.next_button);
        textViewStepNumber = (TextView) findViewById(
                R.id.step_number_text_view);

        mCitationPagerAdaptor = new CitationPagerAdaptor(
                getSupportFragmentManager());

        textViewStepNumber.setText(mArray[mPosition]);

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewStepNumber.setText(mArray[mPosition]);
            }
        });

        mViewPager.setAdapter(mCitationPagerAdaptor);
    }


    class CitationPagerAdaptor extends FragmentStatePagerAdapter {


        public CitationPagerAdaptor(
                FragmentManager fragmentManager) {
            super(fragmentManager);


        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment =CitationFragment.newInstance(mArrayCitationLayout[position]);
            mPosition= position;
            return fragment;
        }





        @Override
        public int getCount() {
            return 4;
        }
    }


}
