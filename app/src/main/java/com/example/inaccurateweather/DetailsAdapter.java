package com.example.inaccurateweather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsAdapter extends ArrayAdapter<DetailModel> {

	private Context context;
    private int layoutResourceID;
    private ArrayList<DetailModel> data;
    private static String IMAGE_URI ="http://openweathermap.org/img/w/";

    public DetailsAdapter(Context context,int layoutResourceID,ArrayList<DetailModel> recordModel_data){
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
            holder.weatherIV= (ImageView) row.findViewById(R.id.weatherIV);
            holder.dateTV=(TextView) row.findViewById(R.id.dateTV);
            holder.longDescriTV =(TextView) row.findViewById(R.id.longDescrTV);
            holder.mornTV = (TextView) row.findViewById(R.id.mornTV);
            holder.eveTV =(TextView) row.findViewById(R.id.EveTV);
            holder.nightTV = (TextView) row.findViewById(R.id.nightTV);
            holder.minTV =(TextView) row.findViewById(R.id.minTV);
            holder.maxTV = (TextView) row.findViewById(R.id.maxTV);
            holder.pressTV =(TextView) row.findViewById(R.id.pressTV);
            holder.windSpTV = (TextView) row.findViewById(R.id.winSpTV);
            holder.degrTV =(TextView) row.findViewById(R.id.degrTV);
            holder.cloudPerTV = (TextView) row.findViewById(R.id.cloudPerTV);
            holder.humTV = (TextView) row.findViewById(R.id.humTV);
            row.setTag(holder);
        }else{
            holder=(RowHolder)row.getTag();
        }

        DetailModel item=data.get(position);
        new DownloadImageTask(holder.weatherIV).execute(IMAGE_URI+item.getDetailsImage()+".png"); 
        holder.dateTV.setText(item.getDetailsDate());   
        holder.longDescriTV.setText(item.getDetailsDescr()); 
        holder.mornTV.setText(item.getDetailsMornTemp()+"°C"); 
        holder.eveTV.setText(item.getDetailsEveTemp()+"°C");
        holder.nightTV.setText(item.getDetailsNigTemp()+"°C"); 
        holder.minTV.setText(item.getDetailsMinTemp()+"°C");
        holder.maxTV.setText(item.getDetailsMaxTemp()+"°C");
        holder.pressTV.setText(item.getDetailsPress()+" hPa");
        holder.windSpTV.setText(item.getDetailsWind()+" m/s");
        holder.degrTV.setText(item.getDetailsDegr()+"°");
        holder.cloudPerTV.setText(item.getDetailsCloud()+"%");
        holder.humTV.setText(item.getDetailsHum()+"%");
        return row;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DetailModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class RowHolder{
        ImageView weatherIV;
        TextView dateTV, longDescriTV, mornTV, eveTV, nightTV, minTV, maxTV, pressTV, windSpTV, degrTV, cloudPerTV, humTV;
    }

    public String getConvertedDate(int i)
    {
        
	SimpleDateFormat fullDate = new SimpleDateFormat("dd-MMM-yyyy");
	Calendar date = new GregorianCalendar();
	date.add(Calendar.DATE, i);
	return fullDate.format(date.getTime());
    }
}
