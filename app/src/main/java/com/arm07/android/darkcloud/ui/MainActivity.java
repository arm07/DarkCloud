package com.arm07.android.darkcloud.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arm07.android.darkcloud.R;
import com.arm07.android.darkcloud.Weather.Current;
import com.arm07.android.darkcloud.Weather.Day;
import com.arm07.android.darkcloud.Weather.Forecast;
import com.arm07.android.darkcloud.Weather.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST="DAILY_FORECAST";

    private Forecast mForecast;
    //moved to updateDisplay() and mForecast obj is used to update the weather
   // private Current mCurrent=mForecast.getCurrent();

    @BindView(R.id.timeLabel)TextView mTimeLabel;
    @BindView(R.id.temperatureTextView)TextView mTemperatureLabel;
    @BindView(R.id.humidityValue)TextView mHunidityLabel;
    @BindView(R.id.precipValueLabel)TextView mPrecipLabel;
    @BindView(R.id.summaryLabel)TextView mSummaryLabel;
    @BindView(R.id.iconImageView)ImageView mImageIconLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String apiKey = "04e74f90d146ebb31f96a0685ef4a312";
        double latitute = 41.9142;
        double longitude = -88.3087;
        String foreCastUrl = "https://api.darksky.net/forecast/" + apiKey + "/" + latitute + "," + longitude;

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(foreCastUrl).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //Response response=call.execute();
                    //Log.v(TAG, response.body().string());
                    String jsonData=response.body().string();
                    if (response.isSuccessful()) {
                        try {

                            mForecast =getForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    upDateDisplay();
                                }
                            });
                        } catch (JSONException e) {
                            //e.printStackTrace();
                            Log.e(TAG,"json Exception caught",e);
                        }
                        //Log.v(TAG,response.body().string());
                    } else {
                        alertUserAboutError();
                    }
                }
            });
        }
        else{
            Toast.makeText(this,"network unavailble",Toast.LENGTH_SHORT).show();
        }
            Log.d(TAG, "main thread");
    }

    private void upDateDisplay() {
        Current mCurrent=mForecast.getCurrent();
        mTemperatureLabel.setText(mCurrent.getmTemperature()+"");
        mTimeLabel.setText("At "+ mCurrent.getFormattedtime()+" it will be");
        mHunidityLabel.setText(mCurrent.getmHumidity()+"");
        mPrecipLabel.setText(mCurrent.getmPrecipChance()+"%");
        mSummaryLabel.setText(mCurrent.getmSummary());
    }

    private Forecast getForecastDetails(String jsonData) throws JSONException{
        Forecast forecast=new Forecast();
        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
            return forecast;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecastObject=new JSONObject(jsonData);
        String timezone=forecastObject.getString("timezone");
        JSONObject hourly=forecastObject.getJSONObject("hourly");
        JSONArray data=hourly.getJSONArray("data");

        Hour[] hours=new Hour[data.length()];
        for (int i=0;i<data.length();i++){
            JSONObject jsonHour=data.getJSONObject(i);
            Hour hour=new Hour();
            hour.setSummary(jsonHour.getString("summary"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimeZone(timezone);
            hours[i]=hour;
        }
        return hours;
    }

    private Day[] getDailyForecast(String jsonData) throws JSONException {

        JSONObject forecastObject=new JSONObject(jsonData);
        String timezone=forecastObject.getString("timezone");
        JSONObject daily=forecastObject.getJSONObject("daily");
        JSONArray data=daily.getJSONArray("data");

       Day[] days=new Day[data.length()];
        for (int i=0;i<data.length();i++){
            JSONObject jsonDay=data.getJSONObject(i);
            Day day=new Day();
            day.setSummary(jsonDay.getString("summary"));
            day.setIcon(jsonDay.getString("icon"));
            day.setMaxTemperature(jsonDay.getDouble("temperatureHigh"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimeZone(timezone);
            days[i]=day;
        }
        return days;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecastObject=new JSONObject(jsonData);
        String timezone=forecastObject.getString("timezone");
        JSONObject currently=forecastObject.getJSONObject("currently");

        Current current =new Current();
        current.setmHumidity(currently.getDouble("humidity"));
        current.setmIcon(currently.getString("icon"));
        current.setmPrecipChance(currently.getDouble("precipProbability"));
        current.setmTemperature(currently.getDouble("temperature"));
        current.setmSummary(currently.getString("summary"));
        current.setmTime(currently.getLong("time"));
        current.setTimeZone(timezone);
        Log.i(TAG,"From JSON: "+ current.getFormattedtime());
        return current;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        boolean isAvailable=false;
        if(networkInfo!=null && networkInfo.isConnected())
            isAvailable=true;
         return isAvailable;
    }

    private void alertUserAboutError() {
        new AlertDialogFragment().show(getFragmentManager(),"error dialog");

    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view){
        Intent intent=new Intent(this,DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST,mForecast.getDailyForecast());
        startActivity(intent);
    }
}
