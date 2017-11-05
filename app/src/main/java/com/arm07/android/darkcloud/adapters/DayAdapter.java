package com.arm07.android.darkcloud.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arm07.android.darkcloud.R;
import com.arm07.android.darkcloud.Weather.Day;

/**
 * Created by rashmi on 11/5/2017.
 */

public class DayAdapter extends BaseAdapter {

    private Context mContext;
    private Day[] mDays;

    public DayAdapter(Context context,Day[] days){
        mContext=context;
        mDays=days;
    }
    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.daily_list_item,null);
            viewHolder=new ViewHolder();
            viewHolder.iconLabel=(ImageView)convertView.findViewById(R.id.iconImageView);
            viewHolder.dayLabel=(TextView)convertView.findViewById(R.id.nameDayLabel);
            viewHolder.temperature=(TextView)convertView.findViewById(R.id.temperatureTextView);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        Day day=mDays[position];
        viewHolder.iconLabel.setImageResource(day.getIconId());
        viewHolder.temperature.setText(day.getMaxTemperature()+"");
        viewHolder.dayLabel.setText(day.getDayOfTheWeek());

        return convertView;
    }

    private static class ViewHolder{
        ImageView iconLabel;
        TextView temperature;
        TextView dayLabel;

    }
}
