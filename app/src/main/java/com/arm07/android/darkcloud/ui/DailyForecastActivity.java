package com.arm07.android.darkcloud.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.arm07.android.darkcloud.R;
import com.arm07.android.darkcloud.Weather.Day;
import com.arm07.android.darkcloud.adapters.DayAdapter;

import java.util.Arrays;

public class DailyForecastActivity extends ListActivity{

    private Day[] mDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        Intent intent=getIntent();
        Parcelable[] parcelable=intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays= Arrays.copyOf(parcelable,parcelable.length,Day[].class);
       /* String[] daysOfTheWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, daysOfTheWeek);

        setListAdapter(mArrayAdapter);*/

        DayAdapter dayAdapter=new DayAdapter(this,mDays);
        setListAdapter(dayAdapter);

    }

}
