package com.example.inaccurateweather;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MainActivity extends Activity {

    private ProgressDialog pDialog;
    private EditText location;
    private TextView country;
    GridView forecastListView;
    ArrayList<RecordModel> forecastList_data = new ArrayList<RecordModel>();
    RowAdapter adapter;
    Intent intent = new Intent();
    Date date = new Date(); 

    private static String LOCATION_ADDRESS = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        location = (EditText) findViewById(R.id.cityEditText);
        country = (TextView)findViewById(R.id.countryDisplayTextView);

        forecastListView = (GridView)findViewById(R.id.forecastListView);

        adapter = new RowAdapter(getApplicationContext(),R.layout.record_model,forecastList_data);

        forecastListView.setAdapter(adapter);

        
        forecastListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					RecordModel clickedObj = (RecordModel) parent
							.getItemAtPosition(position);


					intent.setClass(MainActivity.this, DetailsClass.class);
					intent.putExtra ( "citySequence", location.getText().toString() );
					intent.putExtra("index", String.valueOf(adapter.getItemId(position)));
					
					startActivity(intent); 
				}
			});
        
        
        location.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    pDialog = ProgressDialog.show(v.getContext(), "Loading data", "Keep calm. Data are loading.");
                    JSONAsyncTask task = new JSONAsyncTask();
                    String convertedLocation = location.getText().toString().replace(' ', '-');
                    task.execute(LOCATION_ADDRESS + convertedLocation + "&cnt=10&lang=en&units=metric&mode=json");
                    //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
                return false;
            }
        });
        
    }
		
    void setup(String result){
        try{
            adapter.clear();
            adapter.notifyDataSetChanged();

            JSONObject mainJSONObject = new JSONObject(result);
            JSONObject jsonObject = mainJSONObject.getJSONObject("city");
            country.setText(jsonObject.optString("country"));


            int count = mainJSONObject.getInt("cnt");
            for(int i=0;i<count;i++) {
                JSONObject jsonWeatherObject = new JSONObject(result);

                JSONArray oneDay = jsonWeatherObject.getJSONArray("list");
                JSONObject jRealObject = oneDay.getJSONObject(i);
                JSONObject temperature = jRealObject.getJSONObject("temp");
                

                    JSONArray weatherInfo = jRealObject.getJSONArray("weather");
                    JSONObject weat = weatherInfo.getJSONObject(0);

                String weatherImage = weat.optString("icon");
                //String dayDate = getConvertedDate();

                    adapter.add(new RecordModel(String.valueOf(temperature.optInt("day")), weat.optString("main"), 
                    		weatherImage, adapter.getConvertedDate(i)));

                   // intent.putExtra("count", i);
                    //intent.putExtra("temp"+i, String.valueOf(temperature.optInt("day")));

            }
            adapter.notifyDataSetChanged();

            Toast.makeText(this,"Weather loaded. Click any item to display more.",Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            pDialog.dismiss();
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
