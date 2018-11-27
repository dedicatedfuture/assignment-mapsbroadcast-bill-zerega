package com.billzerega.android.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

public class MapBroadcastReceiver extends BroadcastReceiver {
    private NotificationManager notificationManager;
    private Notification.Builder notificationBuilder;

    public static final int CHANNEL_ID = 1;
    public static final int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;
    public static final String CHANNEL_DESCRIPTION = "BROADCAST MAP CHANNEL";
    public static final String CHANNEL_NAME = "MAPS";

    @Override
    public void onReceive(Context context, Intent intent) {
        Double latitude = intent.getDoubleExtra("Latitude", Double.NaN);
        Double longitude = intent.getDoubleExtra("Longitude", Double.NaN);
        String location = intent.getStringExtra("Location");
        System.out.println("BATMAN broadcast onRecieve from intent: " + latitude + " "
        + longitude + " " + location);

        String hemisphere = getHemisphere(latitude);

        if(hemisphere.equals("NORTH") || hemisphere.equals("SOUTH") || hemisphere.equals("CENTRAL")){
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationBuilder = new Notification.Builder(context, "MAPS");

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

    private NotificationChannel getNotificationChannel(){

        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_NAME, CHANNEL_DESCRIPTION, CHANNEL_IMPORTANCE);
        notificationChannel.setDescription(CHANNEL_DESCRIPTION);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.enableVibration(true);
        notificationChannel.setShowBadge(true);

        return notificationChannel;
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
