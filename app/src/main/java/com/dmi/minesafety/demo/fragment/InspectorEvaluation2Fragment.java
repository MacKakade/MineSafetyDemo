package com.dmi.minesafety.demo.fragment;

import com.dmi.minesafety.demo.R;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the {@link com.dmi.minesafety.demo.fragment.InspectorEvaluation2Fragment}
 * interface to handle interaction events. Use the {@link
 * com.dmi.minesafety.demo.fragment.InspectorEvaluation2Fragment#newInstance}
 * factory method to create an instance of this fragment.
 */
public class InspectorEvaluation2Fragment extends Fragment
        implements TimePickerDialog.OnTimeSetListener {

    View view;

    private TextView mDateButton, mTimeButton, mDateButton2;

    int year, monthOfYear;

    int dayOfMonth;

    int hourOfDay, minute;

    int year2, monthOfYear2;

    int dayOfMonth2;


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
        view = inflater
                .inflate(R.layout.layout_citation_step_two_2, container, false);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(
                calendar.getTimeInMillis()));
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(calendar.MONTH);
        dayOfMonth = calendar.get(calendar.DAY_OF_MONTH);

        year2 = calendar.get(Calendar.YEAR);
        monthOfYear2 = calendar.get(calendar.MONTH);
        dayOfMonth2 = calendar.get(calendar.DAY_OF_MONTH);

        hourOfDay = calendar.get(calendar.HOUR_OF_DAY);
        minute = calendar.get(calendar.MINUTE);

        mDateButton = (TextView) view.findViewById(R.id.btn_date);
        mDateButton2 = (TextView) view.findViewById(R.id.btn_date2);

        mTimeButton = (TextView) view.findViewById(R.id.btn_time);

        mDateButton.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date(
                calendar.getTimeInMillis())));

        mTimeButton.setText(new SimpleDateFormat("HH:mm").format(new Date(
                calendar.getTimeInMillis())));

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), mOnDateSetListener,
                        year,
                        monthOfYear,
                        dayOfMonth);
                datePickerDialog.show();
            }
        });

        mDateButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), mOnDateSetListener2,
                        year2,
                        monthOfYear2,
                        dayOfMonth2);
                datePickerDialog.show();
            }
        });

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(), InspectorEvaluation2Fragment.this,
                        hourOfDay,
                        minute, true);
                timePickerDialog.show();
            }
        });
        return view;
    }


    DatePickerDialog.OnDateSetListener mOnDateSetListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            InspectorEvaluation2Fragment.this.year = year;
            InspectorEvaluation2Fragment.this.monthOfYear = monthOfYear;
            InspectorEvaluation2Fragment.this.dayOfMonth = dayOfMonth;
            mDateButton
                    .setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date(
                            calendar.getTimeInMillis())));
        }
    };


    DatePickerDialog.OnDateSetListener mOnDateSetListener2
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            InspectorEvaluation2Fragment.this.year2 = year;
            InspectorEvaluation2Fragment.this.monthOfYear2 = monthOfYear;
            InspectorEvaluation2Fragment.this.dayOfMonth2 = dayOfMonth;
            mDateButton2
                    .setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date(
                            calendar.getTimeInMillis())));
        }
    };


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
