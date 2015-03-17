package com.dmi.minesafety.demo.activity;

import com.dmi.minesafety.demo.R;
import com.dmi.minesafety.demo.fragment.AttachmentsFragment;
import com.dmi.minesafety.demo.fragment.AutomatedDataFragment;
import com.dmi.minesafety.demo.fragment.InspectorEvaluation2Fragment;
import com.dmi.minesafety.demo.fragment.InspectorEvaluationFragment;
import com.dmi.minesafety.demo.fragment.TerminationActionFragment;
import com.dmi.minesafety.demo.fragment.ViolationDataFragment;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class CitationFormActivity extends ActionBarActivity {

    private PdfDocument document;

    private NonSwipeableViewPager mViewPager;

    private Button mBtnPrevious, mBtnNext;

    private TextView textViewStepNumber;

    private int mPosition = 0;

    private final int MAX_COUNT = 6;

    private File file;

    public int pageNumberPDF = 1;

    public String[] mArray = new String[]{"Section I - Violation Data",
            "Section II - Inspector's Evaluation","Section II - Inspector's Evaluation (Contd)",
            "Section III- Termination Action",
            "Section IV - Automated System Data","Section V - Attachment"};

    private CitationPagerAdaptor mCitationPagerAdaptor;

    private ArrayList<ScrollView> mPdfViews = new ArrayList<>();

    private LinearLayout ll;

    private ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citation);

        ll = new LinearLayout(this);
        putProgressBar();

        file = new File(Environment.getExternalStorageDirectory(),
                "Citation_" + new Date() + ".pdf");
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
                    mPdfViews.remove(mPosition);
                    mViewPager.setCurrentItem(mPosition, true);
                    textViewStepNumber.setText(mArray[mPosition]);
                } else {
                    mPdfViews.clear();
                }
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentStatePagerAdapter fragmentStatePagerAdapter
                        = (FragmentStatePagerAdapter) mViewPager.getAdapter();

                Fragment currentFragment = (Fragment) fragmentStatePagerAdapter
                        .instantiateItem(mViewPager, mPosition);

                if (mPosition != MAX_COUNT - 1) {

                    mPosition++;
                    mPdfViews.add((ScrollView) currentFragment.getView()
                            .findViewById(
                                    R.id.scroll_container));
                    mViewPager.setCurrentItem(mPosition, true);
                    textViewStepNumber.setText(mArray[mPosition]);

                } else {

                    // close the document
                    mPdfViews.add((ScrollView) currentFragment.getView()
                            .findViewById(
                                    R.id.scroll_container));
                    makeDocument();
                    document.close();

                    Toast.makeText(CitationFormActivity.this,
                            "Citation PDF Saved", Toast.LENGTH_LONG).show();
                    finish();

                }


            }
        });

        mViewPager.setAdapter(mCitationPagerAdaptor);
        mViewPager.setOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
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

                        int progress = (position + 1) * (100 / MAX_COUNT);
                        progressBar.setProgress(progress);

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
                    fragment = ViolationDataFragment
                            .newInstance();
                    break;
                case 1:

                    fragment = InspectorEvaluationFragment
                            .newInstance();
                    break;
                case 2:

                    fragment = InspectorEvaluation2Fragment
                            .newInstance();
                    break;
                case 3:
                    fragment = TerminationActionFragment
                            .newInstance();
                    break;
                case 4:
                    fragment = AutomatedDataFragment
                            .newInstance();
                    break;
                case 5:
                    fragment = AttachmentsFragment
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

    public void makeDocument() {

        for (ScrollView view : mPdfViews) {
            // crate a page description
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(
                    view.getChildAt(0).getMeasuredWidth(), view.getChildAt(
                    0).getMeasuredHeight(), pageNumberPDF)
                    .create();

            // start a page
            PdfDocument.Page page = document.startPage(pageInfo);

            // draw something on the page
            ScrollView content = view;

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

            pageNumberPDF++;
        }


    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    private void putProgressBar() {
        // create new ProgressBar and style it
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setProgressDrawable(getResources().getDrawable(
                R.drawable.progress_horizontal_holo_no_background_light));
        progressBar.setProgress(100 / MAX_COUNT);
    }

}
