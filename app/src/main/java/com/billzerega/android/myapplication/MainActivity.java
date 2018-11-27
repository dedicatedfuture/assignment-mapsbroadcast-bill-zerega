package com.billzerega.android.myapplication;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mLatitude;
    private EditText mLongitude;
    private EditText mLocation;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitude = (EditText)findViewById(R.id.latitudeEditText);
        mLongitude = (EditText)findViewById(R.id.longitudeEditText);
        mLocation = (EditText)findViewById(R.id.locationEditText);
        mButton = (Button)findViewById(R.id.mainActivityButton);

        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Double lat = Double.valueOf(mLatitude.getText().toString());
        Double longitude = Double.valueOf(mLongitude.getText().toString());
        String location = mLocation.getText().toString();

        Intent broadcastIntent = new Intent(MainActivity.this, MapBroadcastReceiver.class);

        broadcastIntent.putExtra("Latitude", lat);
        broadcastIntent.putExtra("Longitude", longitude);
        broadcastIntent.putExtra("Location", location);

        sendBroadcast(broadcastIntent);

        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra("Latitude", lat);
        intent.putExtra("Longitude", longitude);
        intent.putExtra("Location", location);

        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
