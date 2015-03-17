package com.dmi.minesafety.demo.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dmi.minesafety.demo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the {@link ViolationDataFragment}
 * interface to handle interaction events. Use the {@link
 * ViolationDataFragment#newInstance} factory method to create an instance of
 * this fragment.
 */
public class ViolationDataFragment extends Fragment
        implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private TextView mDateButton, mTimeButton;

    int year, monthOfYear;

    int dayOfMonth;

    int hourOfDay, minute;

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
        View view = inflater
                .inflate(R.layout.layout_citation_step_one, container, false);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(
                calendar.getTimeInMillis()));
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(calendar.MONTH);
        dayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
        hourOfDay = calendar.get(calendar.HOUR_OF_DAY);
        minute = calendar.get(calendar.MINUTE);

        mDateButton = (TextView) view.findViewById(R.id.btn_date);
        mTimeButton = (TextView) view.findViewById(R.id.btn_time);

        mDateButton.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date(
                calendar.getTimeInMillis())));

        mTimeButton.setText(new SimpleDateFormat("HH:mm").format(new Date(
                calendar.getTimeInMillis())));

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), ViolationDataFragment.this,
                        year,
                        monthOfYear,
                        dayOfMonth);
                datePickerDialog.show();
            }
        });

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(), ViolationDataFragment.this,
                        hourOfDay,
                        minute, true);
                timePickerDialog.show();
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

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        this.year = year;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;
        mDateButton.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date(
                calendar.getTimeInMillis())));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        if (minute >= 0 && minute <= 9) {
            mTimeButton.setText(hourOfDay + ":0" + minute);
        } else {
            mTimeButton.setText(hourOfDay + ":" + minute);
        }
    }

}
