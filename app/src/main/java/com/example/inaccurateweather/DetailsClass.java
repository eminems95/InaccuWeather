package com.example.inaccurateweather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;










import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsClass extends Activity {

	TextView dayDetails, cityDetails, temperatureDetails;
	ProgressDialog progressDialog;
	DetailsAdapter detailsAdapter;
	ArrayList<DetailModel> detailsList_data = new ArrayList<DetailModel>();
	GridView detailsGridView;
	String indexString;
	Intent intent;
	private static String LOCATION_ADDRESS = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_dialog_layout);
		
		
		
		Bundle bundle = this.getIntent().getExtras();
		String citySequence = bundle.getString("citySequence");
		
		

		cityDetails = (TextView)findViewById(R.id.locationDetailsTV);
		detailsGridView = (GridView)findViewById(R.id.detailsGridView);
		cityDetails.setText(citySequence);
		
		detailsAdapter = new DetailsAdapter(getApplicationContext(),R.layout.details_model,detailsList_data);
		
		detailsGridView.setAdapter(detailsAdapter);

		JSONAsyncTask detailsTask = new JSONAsyncTask();
		
		detailsTask.execute(LOCATION_ADDRESS + citySequence.replace(' ', '-') + "&cnt=10&lang=en&units=metric&mode=json");		
			
		
		
	}

	void setup(String result){
        try{           
        	
        	detailsAdapter.clear();
            detailsAdapter.notifyDataSetChanged();

            JSONObject mainJSONObject = new JSONObject(result);
            JSONObject jsonObject = mainJSONObject.getJSONObject("city");
            //JSONArray recentPosts = jsonObject.getJSONArray("city");
            //JSONObject jRealObject = jsonObject.getJSONObject(0);


            int count = mainJSONObject.getInt("cnt");
            for(int i=0;i<count;i++) {
                JSONObject jsonWeatherObject = new JSONObject(result);

                JSONArray oneDay = jsonWeatherObject.getJSONArray("list");
                JSONObject jRealObject = oneDay.getJSONObject(i);
                JSONObject temperature = jRealObject.getJSONObject("temp");
                

                    JSONArray weatherInfo = jRealObject.getJSONArray("weather");
                    JSONObject weat = weatherInfo.getJSONObject(0);

                String weatherImage = weat.optString("icon");
                String weatherDate = detailsAdapter.getConvertedDate(i);
                String weatherLongDescr = weat.optString("description");
                String weatherMorning = String.valueOf(temperature.optInt("morn"));
                String weatherEvening = String.valueOf(temperature.optInt("eve"));
                String weatherNight = String.valueOf(temperature.optInt("night"));
                String weatherMin = String.valueOf(temperature.optInt("min"));
                String weatherMax = String.valueOf(temperature.optInt("max"));
                String weatherPressure = jRealObject.optString("pressure");
                String weatherWindSpeed = jRealObject.optString("speed");
                String weatherWindDegrees = jRealObject.optString("deg");
                String weatherClouds = jRealObject.optString("clouds");
                String weatherHumidity = jRealObject.optString("humidity");
                
                    detailsAdapter.add(new DetailModel(weatherImage, weatherDate, weatherLongDescr, 
                    		weatherMorning, weatherEvening, weatherNight, 
                    		weatherMin, weatherMax, weatherPressure, weatherWindSpeed, 
                    		weatherWindDegrees, weatherClouds, weatherHumidity));
            }
            detailsAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            
        }

    }

    private class JSONAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            InputStream iStream = null;
            String json = "";
            String stringURL = params[0];

            try{
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(stringURL);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                iStream = httpEntity.getContent();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return e.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }

            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(iStream,"UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = reader.readLine())!=null){
                    sb.append(line);
                }
                iStream.close();
                json = sb.toString();
                return json;
            } catch (Exception e) {
                Log.e("Buffer error", "Error converting result" + e.toString());
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result){
            setup(result);
            super.onPostExecute(result);
        }
    }
	
}
