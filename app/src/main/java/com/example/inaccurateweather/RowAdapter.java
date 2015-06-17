package com.example.inaccurateweather;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import static android.graphics.Color.CYAN;
import static android.graphics.Color.RED;

public class RowAdapter extends ArrayAdapter<RecordModel> {

    private Context context;
    private int layoutResourceID;
    private ArrayList<RecordModel> data;
    private static String IMAGE_URI ="http://openweathermap.org/img/w/";

    public RowAdapter(Context context,int layoutResourceID,ArrayList<RecordModel> recordModel_data){
        super(context,layoutResourceID,recordModel_data);
        this.context=context;
        this.layoutResourceID=layoutResourceID;
        this.data=recordModel_data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row=convertView;
        RowHolder holder=null;

        if(row==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            row=inflater.inflate(layoutResourceID,parent,false);
            holder=new RowHolder();
            holder.weatherIV= (ImageView) row.findViewById(R.id.weatherImageView);
            holder.dayTempTV=(TextView) row.findViewById(R.id.dayTempTextView);
            holder.shortDescriTV =(TextView) row.findViewById(R.id.shortDescriptionTextView);
            holder.dateTV = (TextView) row.findViewById(R.id.dateTextView);
            row.setTag(holder);
        }else{
            holder=(RowHolder)row.getTag();
        }

        RecordModel item=data.get(position);
        //holder.weatherIV.setImageResource();
        if(Double.parseDouble(item.getDayTempValue())>0) {
            holder.dayTempTV.setTextColor(Color.rgb(200,50,50));
            holder.dayTempTV.setText(item.getDayTempValue()+"°C");
        }else if(Double.parseDouble(item.getDayTempValue())<=0) {
            holder.dayTempTV.setTextColor(Color.rgb(50,50,200));
            holder.dayTempTV.setText(item.getDayTempValue()+"°C");
        }
        holder.shortDescriTV.setText(item.getShortDescription());
       new DownloadImageTask(holder.weatherIV).execute(IMAGE_URI+item.getImage()+".png");
        holder.dateTV.setText(item.getDateValue());
        return row;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public RecordModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class RowHolder{
        ImageView weatherIV;
        TextView dateTV;
        TextView dayTempTV;
        TextView shortDescriTV;
    }
    
    public String getConvertedDate(int i)
    {
        
	SimpleDateFormat fullDate = new SimpleDateFormat("dd-MMM-yyyy");
	Calendar date = new GregorianCalendar();
	date.add(Calendar.DATE, i);
	return fullDate.format(date.getTime());
    }
    
}