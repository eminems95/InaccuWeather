package com.example.inaccurateweather;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Marcin on 2015-03-02.
 */
public class RecordModel {
    private String dayTempValue, shortDescription, dateValue;
    private String weatherImage;

    public RecordModel(String dayTempValue, String shortDescription, String weatherImage, String dateValue){
        this.dayTempValue = dayTempValue;
        this.shortDescription = shortDescription;
        this.weatherImage = weatherImage;
        this.dateValue = dateValue;
    }

    public String getDayTempValue() {
        return dayTempValue;
    }

    public String getShortDescription() {
        return shortDescription;
    }


    public String getImage() {
        return weatherImage;
    }

	public String getDateValue() {
		return dateValue;
	}

}