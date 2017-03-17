package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.quakereport.model.EarthQuakeData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.security.AccessController.getContext;


/**
 * Created by saunakc on 28/02/17.
 */

public class CustomAdapter extends BaseAdapter {


    ArrayList<EarthQuakeData> myDataList;
    Context context;

    TextView tv_Magnitude;
    TextView tv_location;
    TextView tv_Date;
    TextView tv_Time;
    TextView tv_SecondaryLoc;
    GradientDrawable magnitudeCircle;

    public CustomAdapter(ArrayList<EarthQuakeData> myDataList, Context context) {
        this.myDataList = myDataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        int count = myDataList.size();
        return count;
    }

    @Override
    public Object getItem(int position) {
        return myDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.custom_layout, null);
        tv_Time = (TextView) convertView.findViewById(R.id.time);
        tv_Date = (TextView) convertView.findViewById(R.id.date);
        tv_location = (TextView) convertView.findViewById(R.id.location);
        tv_SecondaryLoc = (TextView) convertView.findViewById(R.id.secondaryLoc);
        tv_Magnitude = (TextView) convertView.findViewById(R.id.magnitude);

        //Getting the reference of magnitude Circle.
        magnitudeCircle = (GradientDrawable) tv_Magnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(myDataList.get(position).getMagnitude(),context);
        magnitudeCircle.setColor(magnitudeColor);


        tv_Date.setText(getFormattedDate(myDataList.get(position).getDate()));
        tv_Time.setText(getFormattedTime(myDataList.get(position).getDate()));
//        tv_Date.setText(myDataList.get(position).getDate());
        tv_Magnitude.setText(String.valueOf(myDataList.get(position).getMagnitude()));
//        tv_location.setText(myDataList.get(position).getLocation());

        tv_SecondaryLoc.setText(getFormattedSecondaryLocation(myDataList.get(position).getLocation()));

        tv_location.setText(getFormattedPrimaryLocation(myDataList.get(position).getLocation()));
        return convertView;
    }

    private int getMagnitudeColor(double magnitude,Context context) {
        int colourOfMagCircle;
        int mag = (int) magnitude;

        switch (mag) {
            case 0:
                colourOfMagCircle = R.color.magnitude1;
                break;
            case 1:
                colourOfMagCircle = R.color.magnitude1;
                break;
            case 2:
                colourOfMagCircle = R.color.magnitude2;
                break;
            case 3:
                colourOfMagCircle = R.color.magnitude3;
                break;
            case 4:
                colourOfMagCircle = R.color.magnitude4;
                break;
            case 5:
                colourOfMagCircle = R.color.magnitude5;
                break;
            case 6:
                colourOfMagCircle = R.color.magnitude6;
                break;
            case 7:
                colourOfMagCircle = R.color.magnitude7;
                break;
            case 8:
                colourOfMagCircle = R.color.magnitude8;
                break;
            case 9:
                colourOfMagCircle = R.color.magnitude9;
                break;
            default:
                colourOfMagCircle = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(context, colourOfMagCircle);
    }


    public String getFormattedSecondaryLocation(String location) {

        String secondaryLoc = location.substring(1, (location.indexOf("of") + 2));
//        location.indexOf(" ",location.indexOf(" ")+1);
//        String secondaryLoc = location.substring(location.indexOf(" ",location.indexOf(" ")));
        return secondaryLoc;
    }


    public String getFormattedPrimaryLocation(String location) {
        String primaryLocation = location.substring(location.indexOf("of") + 3);
//        String primaryLocation = location.substring(location.indexOf(location.indexOf(",")));
//        location.indexOf(" ",location.indexOf(" ")+1);
        return primaryLocation;
    }

    public String getFormattedDate(String unixDate) {
        long timeInMilliseconds = Long.valueOf(unixDate);
        Date dateObject = new Date(timeInMilliseconds);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        return dateToDisplay;
    }

    public String getFormattedTime(String unixDate) {
        long timeInMilliseconds = Long.valueOf(unixDate);
        Date dateObject = new Date(timeInMilliseconds);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a");
        String dateToDisplay = dateFormatter.format(dateObject);
        return dateToDisplay;
    }
}
