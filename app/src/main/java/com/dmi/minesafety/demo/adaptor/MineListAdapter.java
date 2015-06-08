package com.dmi.minesafety.demo.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dmi.minesafety.demo.R;
import com.dmi.minesafety.demo.dummy.DummyContent;

import java.util.List;

/**
 * Created by Mandar on 3/4/2015.
 */
public class MineListAdapter extends ArrayAdapter<DummyContent.Mine> {

    LayoutInflater mLayoutInflater;

    List<DummyContent.Mine> objects;

    int minLength;

    public MineListAdapter(Context context, int resource,
                           List<DummyContent.Mine> objects) {
        super(context, resource, objects);
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objects = objects;

        for(DummyContent.Mine mine : objects) {
            if (mine.city.length() > minLength) {
                minLength = mine.city.length();
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DummyContent.Mine mine = objects.get(position);

        ViewHolder holder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_spinner_item_mines,
                    null,
                    false);

            holder = new ViewHolder();

            holder.cityTextView = ((TextView) convertView.findViewById(R.id.text_mine_city));
            holder.cityTextView.setMinEms(minLength / 2);
            holder.nameTextView = ((TextView) convertView.findViewById(R.id.text_mine_name));
            holder.stateTextView = ((TextView) convertView.findViewById(R.id.text_mine_state));
            holder.operatorNameTextView = ((TextView) convertView.findViewById(R.id.text_mine_operator));
            holder.mineIdTextView = ((TextView) convertView.findViewById(R.id.text_mine_id));
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.cityTextView.setText(mine.city);
        holder.nameTextView.setText(mine.name);
        holder.stateTextView.setText(mine.state);
        holder.operatorNameTextView.setText(mine.operatorName);
        holder.mineIdTextView.setText(mine.id);

        return convertView;
    }

    static class ViewHolder {
        TextView cityTextView, nameTextView, stateTextView, operatorNameTextView, mineIdTextView;
    }

}

















