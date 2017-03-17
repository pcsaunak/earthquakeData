/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.quakereport.model.EarthQuakeData;
import com.example.android.quakereport.networking.DownloadCallbacks;
import com.example.android.quakereport.networking.EarthQuakeAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements DownloadCallbacks,ListView.OnItemClickListener {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    ArrayList<EarthQuakeData> earthQuakeDataList;
    ListView earthquakeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        EarthQuakeAsyncTask mGetDataTask = new EarthQuakeAsyncTask(this);
        mGetDataTask.execute();

        // Find a reference to the {@link ListView} in the layout
         earthquakeListView = (ListView) findViewById(R.id.list);



    }

    @Override
    public void onSuccess(EarthQuakeAsyncTask.Result result) {
        System.out.println("Successfully obtained result");
        String outputStr = result.getOutput();
        earthQuakeDataList= new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(outputStr);
            JSONArray jsonArray = jsonObject.getJSONArray("features");


            for (int i=0;i<jsonArray.length();i++){
                JSONObject featuresJsonObj = jsonArray.getJSONObject(i);
                JSONObject propertiesJsonObj = featuresJsonObj.getJSONObject("properties");

                EarthQuakeData myEarthQuakeData = new EarthQuakeData();
                myEarthQuakeData.setLocation(propertiesJsonObj.getString("place"));
                myEarthQuakeData.setDate(propertiesJsonObj.getString("time"));
                myEarthQuakeData.setMagnitude(propertiesJsonObj.getDouble("mag"));
                myEarthQuakeData.setUrl(propertiesJsonObj.getString("url"));
                earthQuakeDataList.add(myEarthQuakeData);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        CustomAdapter customAdapter = new CustomAdapter(earthQuakeDataList,this);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(customAdapter);
        earthquakeListView.setOnItemClickListener(this);
        customAdapter.notifyDataSetChanged();
    }


    @Override
    public void onFailed() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(this, "Clicked data: " +earthQuakeDataList.get(position).getUrl(), Toast.LENGTH_SHORT).show();
        goToUrl(earthQuakeDataList.get(position).getUrl());
    }


    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
