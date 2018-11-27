package com.billzerega.android.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MapBroadcastReceiver extends BroadcastReceiver {
    private NotificationManager notificationManager;
    private Notification.Builder notificationBuilder;


    @Override
    public void onReceive(Context context, Intent intent) {
        Double latitude = intent.getDoubleExtra("Latitude", Double.NaN);
        Double longitude = intent.getDoubleExtra("Longitude", Double.NaN);
        String location = intent.getStringExtra("Location");

        String hemisphere = getHemisphere(latitude);

        if(hemisphere.equals("NORTH") || hemisphere.equals("SOUTH") || hemisphere.equals("CENTRAL")){
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationBuilder = new Notification.Builder(context);

            notificationBuilder.setSmallIcon(R.drawable.broadcast);
            notificationBuilder.setContentTitle(location);
            notificationBuilder.setContentText("Location Unknown: Located in the " + hemisphere+
            " hemisphere, with the coordinates (lat, lng): " + Double.toString(latitude) + ", " +
                    Double.toString(longitude));

            notificationManager.notify(1, notificationBuilder.build());


        }else{
            Log.d("ZEREGA", "onReceive: outisde of earths bounds");
        }






    }



    private String getHemisphere(Double latitude){

        String hemisphere = "";
        boolean isCentralHemisphere = (latitude < 23 && latitude > -23);
        boolean isNorthHemisphere = (latitude > 23 && latitude <= 90);
        boolean isSouthHemisphere = (latitude < -23 && latitude >= -90);

        if(isCentralHemisphere)
            hemisphere = "CENTRAL";
        else{
            if (isNorthHemisphere) hemisphere = "NORTH";
            else{
                if (isSouthHemisphere)
                    hemisphere = "SOUTH";
            }
        }

        return hemisphere;
    }
}
