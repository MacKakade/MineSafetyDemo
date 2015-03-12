package com.dmi.minesafety.demo.activity;

import com.dmi.minesafety.demo.R;
import com.dmi.minesafety.demo.fragment.AutomatedDataFragment;
import com.dmi.minesafety.demo.fragment.InspectorEvaluationFragment;
import com.dmi.minesafety.demo.fragment.TerminationActionFragment;
import com.dmi.minesafety.demo.fragment.VoilationDataFragment;
import com.dmi.minesafety.demo.widget.NonSwipeableViewPager;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class CitationFormActivity extends ActionBarActivity {

    PdfDocument document;

    NonSwipeableViewPager mViewPager;

    Button mBtnPrevious;

    Button mBtnNext;

    TextView textViewStepNumber;

    int mPosition = 0;

    final int MAX_COUNT = 4;

    File file;

    public int pageNumberPDF = 1;

    public String[] mArray = new String[]{"Section I - Violation Data",
            "Section II - Inspector's Evaluation",
            "Section III- Termination Action",
            "Section IV - Automated System Data"};

    private CitationPagerAdaptor mCitationPagerAdaptor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citation);

        file = new File(Environment.getExternalStorageDirectory(), "new.pdf");
        document = new PdfDocument();
        mViewPager = (NonSwipeableViewPager) findViewById(R.id.pager);
        mBtnPrevious = (Button) findViewById(R.id.previous_button);
        mBtnNext = (Button) findViewById(R.id.next_button);
        textViewStepNumber = (TextView) findViewById(
                R.id.step_number_text_view);

        mCitationPagerAdaptor = new CitationPagerAdaptor(
                getSupportFragmentManager());

        textViewStepNumber.setText(mArray[mPosition]);

        mBtnPrevious.setVisibility(View.GONE);

        mBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPosition != 0) {
                    mPosition--;
                    mViewPager.setCurrentItem(mPosition, true);
                    textViewStepNumber.setText(mArray[mPosition]);
                }
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPosition != MAX_COUNT - 1) {
                    mPosition++;
                    mViewPager.setCurrentItem(mPosition, true);
                    textViewStepNumber.setText(mArray[mPosition]);
                } else {
                   // makeDocument();
                }
            }
        });

        mViewPager.setAdapter(mCitationPagerAdaptor);
        mViewPager
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position,
                            float positionOffset,
                            int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (mPosition == 0) {
                            mBtnPrevious.setVisibility(View.GONE);
                        } else {
                            mBtnPrevious.setVisibility(View.VISIBLE);
                        }

                        if (mPosition == MAX_COUNT - 1) {
                            mBtnNext.setText("SUBMIT");
                            mBtnNext.setCompoundDrawablesWithIntrinsicBounds(
                                    null, null, null, null);

                        } else {
                            mBtnNext.setText("");
                            mBtnNext.setCompoundDrawablesWithIntrinsicBounds(
                                    null, null, getResources().getDrawable(
                                            R.drawable.ic_next), null);
                        }

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
    }


    class CitationPagerAdaptor extends FragmentStatePagerAdapter {

        public CitationPagerAdaptor(
                FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = VoilationDataFragment
                            .newInstance();
                    break;
                case 1:
                    fragment = InspectorEvaluationFragment
                            .newInstance();
                    break;
                case 2:
                    fragment = TerminationActionFragment
                            .newInstance();
                    break;
                case 3:
                    fragment = AutomatedDataFragment
                            .newInstance();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return MAX_COUNT;
        }
    }


    public void makeDocument(View view) {

        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(
                getScreenWidth(this), getScreenHeight(this), pageNumberPDF).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        // draw something on the page
        View content = view;
        content.draw(page.getCanvas());

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            System.err.println("Error while creating FileOutputStream");
        }
        // finish the page
        document.finishPage(page);

        try {
            document.writeTo(os);
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        // close the document
        document.close();

        pageNumberPDF++;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }


}
