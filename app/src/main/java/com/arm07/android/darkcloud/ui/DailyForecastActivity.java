package com.arm07.android.darkcloud.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arm07.android.darkcloud.R;
import com.arm07.android.darkcloud.adapters.DayAdapter;
import com.arm07.android.darkcloud.weather.Day;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyForecastActivity extends ListActivity{

    private Day[] mDays;

    @BindView(android.R.id.list)
    ListView mListView;
    @BindView(android.R.id.empty)
    TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        ButterKnife.bind(this);

        Intent intent=getIntent();
        Parcelable[] parcelable=intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays= Arrays.copyOf(parcelable,parcelable.length,Day[].class);

       /* String[] daysOfTheWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, daysOfTheWeek);

        setListAdapter(mArrayAdapter);*/

        DayAdapter dayAdapter=new DayAdapter(this,mDays);
       // setListAdapter(dayAdapter);
        mListView.setAdapter(dayAdapter);
        mListView.setEmptyView(mEmptyTextView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String dayOfTheWeek = mDays[position].getDayOfTheWeek();
                String conditions = mDays[position].getSummary();
                String highTemp = mDays[position].getMaxTemperature() + "";
                String message = String.format("On %s the high will be %s and it will be %s", dayOfTheWeek, highTemp, conditions);
                Toast.makeText(DailyForecastActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }

}
