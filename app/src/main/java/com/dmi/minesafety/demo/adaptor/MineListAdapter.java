package com.dmi.minesafety.demo.adaptor;

import com.dmi.minesafety.demo.R;
import com.dmi.minesafety.demo.dummy.DummyContent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mandar on 3/4/2015.
 */
public class MineListAdapter extends ArrayAdapter<DummyContent.Mine> {

    LayoutInflater mLayoutInflater;
    List<DummyContent.Mine> objects;


    public MineListAdapter(Context context, int resource,
            List<DummyContent.Mine> objects) {
        super(context, resource, objects);
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DummyContent.Mine mine = objects.get(position);
        View v =
                mLayoutInflater
                        .inflate(R.layout.layout_spinner_item_mines, null,
                                false);

        ((TextView) v.findViewById(R.id.text_mine_name)).setText(
                mine.name);
        ((TextView) v.findViewById(R.id.text_mine_state)).setText(
                mine.state);
        ((TextView) v.findViewById(R.id.text_mine_city)).setText(
                mine.city);
        ((TextView) v.findViewById(R.id.text_mine_operator)).setText(
                mine.operatorName);

        ((TextView) v.findViewById(R.id.text_mine_id))
                .setText(mine.id);

        return v;
    }
}

















