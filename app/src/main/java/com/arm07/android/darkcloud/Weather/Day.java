package com.arm07.android.darkcloud.Weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by rashmi on 11/4/2017.
 */

public class Day implements Parcelable {

    private long mTime;
    private String mSummary;
    private double mMaxTemperature;
    private String mIcon;
    private String mTimeZone;

    public Day(){

    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getMaxTemperature() {
        return (int)Math.round(mMaxTemperature);
    }

    public void setMaxTemperature(double maxTemperature) {
        mMaxTemperature = maxTemperature;
    }

    public String getIcon() {
        return mIcon;
    }

    //calling the forecast getIconId()
    public int getIconId(){
        return Forecast.getIconId(mIcon);
    }

    public String getDayOfTheWeek(){

        SimpleDateFormat formatter=new SimpleDateFormat("EEEE");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        Date dateTime=new Date(getTime()*1000);
        String timeString=formatter.format(dateTime);
        return timeString;
    }
    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTime);
        dest.writeString(mTimeZone);
        dest.writeString(mSummary);
        dest.writeDouble(mMaxTemperature);
        dest.writeString(mIcon);
    }

    private Day(Parcel in){
        mTime=in.readLong();
        mTimeZone=in.readString();
        mSummary=in.readString();
        mMaxTemperature=in.readDouble();
        mIcon=in.readString();
    }

    public static final Creator<Day> CREATOR=new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

}

