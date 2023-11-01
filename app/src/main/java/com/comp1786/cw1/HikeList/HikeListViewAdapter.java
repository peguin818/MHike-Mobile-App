package com.comp1786.cw1.HikeList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.comp1786.cw1.Entity.Hike;
import com.comp1786.cw1.Homepage_Activity;
import com.comp1786.cw1.R;

import java.util.List;

public class HikeListViewAdapter extends BaseAdapter {

    Context context;
    List<Hike> hikeList;
    LayoutInflater layoutInflater;

    public HikeListViewAdapter(Context context, List<Hike> hikeList) {
        this.context = context;
        this.hikeList = hikeList;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return hikeList.size();
    }

    @Override
    public Object getItem(int i) {
        return hikeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.activity_hike_list_view_item, null);
        TextView textView = (TextView) view.findViewById(R.id.hikeListViewItemName);
        textView.setText(hikeList.get(position).getHikeName());
        return view;
    }


}
