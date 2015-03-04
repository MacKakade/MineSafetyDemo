package com.dmi.minesafety.demo;

import com.dmi.minesafety.demo.dummy.DummyContent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Mandar on 3/4/2015.
 */
class MineListAdapter extends ArrayAdapter<DummyContent.Mine> {

    LayoutInflater mLayoutInflater;

    public MineListAdapter(Context context, int resource,
            DummyContent.Mine[] objects) {
        super(context, resource, objects);
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v =
                mLayoutInflater
                        .inflate(R.layout.layout_spinner_item_mines, null,
                                false);

        ((TextView) v.findViewById(R.id.text_mine_name)).setText(
                DummyContent.MINES.get(position).name);
        ((TextView) v.findViewById(R.id.text_mine_state)).setText(
                DummyContent.MINES.get(position).state);
        ((TextView) v.findViewById(R.id.text_mine_city)).setText(
                DummyContent.MINES.get(position).city);
        ((TextView) v.findViewById(R.id.text_mine_operator)).setText(
                DummyContent.MINES.get(position).operatorName);

        ((TextView) v.findViewById(R.id.text_mine_id))
                .setText(DummyContent.MINES.get(position).id);

        return v;
    }
}

















