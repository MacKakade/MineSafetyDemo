package com.dmi.minesafety.demo;

import com.dmi.minesafety.demo.dummy.DummyContent;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mandar on 3/3/2015.
 */
public class SearchAdapter extends CursorAdapter {

    private List<DummyContent.Mine> items;

    private TextView text;

    public SearchAdapter(Context context, Cursor cursor, List<DummyContent.Mine> items) {

        super(context, cursor, false);

        this.items = items;

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.layout_search_item, parent, false);

        text = (TextView) view.findViewById(R.id.search_text);

        return view;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(cursor.getPosition()<items.size()) {
            text.setText(items.get(cursor.getPosition()).id);
        }
    }

}


