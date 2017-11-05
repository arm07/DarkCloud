package com.arm07.android.darkcloud.Weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by rashmi on 11/4/2017.
 */

public class Current {
    private String mIcon;
    private long mTime;
    private double mTemperature,mHumidity,mPrecipChance;
    private String mSummary;
    private String mTimeZone;

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String mTimeZone) {
        this.mTimeZone = mTimeZone;
    }

    public String getmIcon() {
        return mIcon;
    }

    //// to avoid DRY principle refactor code to Forecast class
    public int getIconId(){
       return Forecast.getIconId(mIcon);
    }
    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public long getTime() {
        return mTime;
    }
    public String getFormattedtime(){
        SimpleDateFormat formatter=new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime=new Date(getTime()*1000);
        String timeString=formatter.format(dateTime);
        return timeString;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }

    public int getmTemperature() {
        return (int) Math.round(mTemperature);
    }

    public void setmTemperature(double mTemperature) {
        this.mTemperature = mTemperature;
    }

    public double getmHumidity() {
        return mHumidity;
    }

    public void setmHumidity(double mHumidity) {
        this.mHumidity = mHumidity;
    }

    public int getmPrecipChance() {
        double prcipProbab=mPrecipChance*100;
        return (int) Math.round(prcipProbab);
    }

    public void setmPrecipChance(double mPrecipChance) {
        this.mPrecipChance = mPrecipChance;
    }

    public String getmSummary() {
        return mSummary;
    }

    public void setmSummary(String mSummary) {
        this.mSummary = mSummary;
    }
}
