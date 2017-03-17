package com.example.android.quakereport.networking;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by saunakc on 02/03/17.
 */

public class EarthQuakeAsyncTask extends AsyncTask<String, Void, EarthQuakeAsyncTask.Result> {

    public DownloadCallbacks<String> mCallBack;

    public EarthQuakeAsyncTask(DownloadCallbacks<String> mCallBack) {
        this.mCallBack = mCallBack;
    }

    public static class Result {
        public String output;
        public Exception exception;

        public Result(String output) {
            this.output = output;
        }

        public Result(Exception exception) {
            this.exception = exception;
        }

        public String getOutput() {
            return output;
        }
    }

    public String requestURL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10";


    @Override
    protected Result doInBackground(String... params) {
        Result result = null;
        if (!isCancelled() && requestURL != null) {
            try {
                result = new Result(getData());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String getData() throws IOException, JSONException {
        Log.d("#######", "Inside download URL ......");
        URL url = new URL(requestURL);
        InputStream stream = null;
        HttpURLConnection connection = null;
        String result = null;
        JSONObject jsonObject = null;
        jsonObject = getJSONObjectFromURL(url);
        result = jsonObject.toString();
        return result;

    }


    public static JSONObject getJSONObjectFromURL(URL urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;
        URL url = urlString;
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(15000 /* milliseconds */);
        urlConnection.setDoOutput(true);
        urlConnection.connect();


        System.out.println("Open Stream #### " + url.openStream().toString());
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String jsonString = new String();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        jsonString = sb.toString();
        return new JSONObject(jsonString);
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);

        if (result != null || !result.equals("")) {
            mCallBack.onSuccess(result);
        } else {
            mCallBack.onFailed();
        }
    }
}
