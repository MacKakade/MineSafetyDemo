package com.dmi.mobile.inspection.adaptor;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmi.mobile.inspection.R;
import com.dmi.mobile.inspection.dummy.DummyContent;

import java.util.List;

/**
 * Created by Mandar on 3/3/2015.
 */
public class SearchAdapter extends CursorAdapter {

    private List<DummyContent.Mine> items;

    private TextView txvId, txvName;

    public SearchAdapter(Context context, Cursor cursor, List<DummyContent.Mine> items) {
        super(context, cursor, false);
        this.items = items;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_search_item, parent, false);
        txvId = (TextView) view.findViewById(R.id.search_text_id);
        txvName = (TextView) view.findViewById(R.id.search_text_name);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(cursor.getPosition()<items.size()) {
            txvId.setText(items.get(cursor.getPosition()).id);
            txvName.setText(items.get(cursor.getPosition()).name);
        }
    }

}


