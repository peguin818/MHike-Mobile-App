package com.comp1786.cw1.ObservationList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.comp1786.cw1.Entity.Hike;
import com.comp1786.cw1.Entity.Observation;
import com.comp1786.cw1.R;

import java.util.List;

public class ObservationListViewAdapter extends BaseAdapter {

    Context context;
    List<Observation> obsList;
    LayoutInflater layoutInflater;

    public ObservationListViewAdapter(Context context, List<Observation> obsList) {
        this.context = context;
        this.obsList = obsList;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return obsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.activity_observation_list_view_item, null);
        TextView textView = (TextView) view.findViewById(R.id.obsListViewItemName);
        textView.setText((int) obsList.get(position).getId());
        return view;
    }
}