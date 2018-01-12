package com.kuruvatech.santoshkenchaamba;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kuruvatech.santoshkenchaamba.utils.Constants;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.firebase.client.ValueEventListener;

//import khaanavali.customer.utils.Constants;
//import khaanavali.customer.utils.SessionManager;


public class NotificationListener extends Service {

    Bitmap bitmap;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //When the service is started
    @Override
    public int onStartCommand(Intent iintent, int flags, int startId) {


        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference();



        //Adding a valueevent listener to firebase
        //this will help us to  track the value changes on firebase
        mPostReference.addValueEventListener(new ValueEventListener() {

            //This method is called whenever we change the value in firebase
            @Override
            public void onDataChange(DataSnapshot snapshot) {

//                String msg = snapshot.getValue().toString();
//
//                showNotification(Calendar.getInstance().getTimeInMillis(), msg, 2);

                if (snapshot.child(Constants.USERNAME).exists()) {
                    DataSnapshot childds= snapshot.child(Constants.USERNAME);
                    String message ="",heading = "", imageuri= "";
                    if(childds.child("message").exists())
                        message = childds.child("message").getValue().toString();
                    if(childds.child("heading").exists())
                        heading = childds.child("heading").getValue().toString();
                    if(childds.child("image").exists()) {
                        imageuri = childds.child("image").getValue().toString();

                        Toast.makeText(getApplicationContext(),imageuri, Toast.LENGTH_LONG).show();
                    }
                     getBitmapfromUrl("https://s3.ap-south-1.amazonaws.com/chunavane/hdk/images.jpg");
                   // sendNotification(message, heading,bitmap);
                    // heading = remoteMessage.getData().get("image");
                   //  imageUri = remoteMessage.getData().get("image");

                  //  snapshot.child("hdk").child("heading")
                   //     showNotification(Calendar.getInstance().getTimeInMillis(), msg, 2);

                }
//                //message will contain the Push Message
//                String message = snapshot.getValue().getClass(Constants.USERNAME);
//                //imageUri will contain URL of the image to be displayed with Notification
//                String imageUri = remoteMessage.getData().get("image");
//                //If the key AnotherActivity has  value as True then when the user taps on notification, in the app AnotherActivity will be opened.
//                //If the key AnotherActivity has  value as False then when the user taps on notification, in the app MainActivity will be opened.
//                String TrueOrFlase = remoteMessage.getData().get("AnotherActivity");

                //To get a Bitmap image from the URL received

//                sendNotification(message, bitmap);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }


        });

        return START_STICKY;
    }

    private void sendNotification(String messageBody, String heading, Bitmap image) {
        if(bitmap == null)
        {
            Toast.makeText(getApplicationContext(),"bitmap null", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new android.support.v4.app.NotificationCompat.Builder(this)
                .setLargeIcon(image)/*Notification icon image*/
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(heading)
                .setContentText(messageBody)
                .setStyle(new android.support.v4.app.NotificationCompat.BigPictureStyle()
                        .bigPicture(image))/*Notification with Image*/
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void showNotification(long when, String msg, int intent_type) {
        //Creating a notification
        final String GROUP_KEY_ORDER_IDS = "group_order_ids";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_push_notificationgo);

        //Vibration
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        //LED
        builder.setLights(Color.RED, 3000, 3000);
        //Sound
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        Intent intent;

        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=khaanavali.customer"));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("Election");
        builder.setContentText(msg);
        builder.setAutoCancel(true);
        builder.setWhen(when);
//        builder.setGroup(GROUP_KEY_ORDER_IDS);
//        builder.setGroupSummary(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((int) when, builder.build());
    }

    public void getBitmapfromUrl(String imageUrl) {
//        try {
//            URL url = new URL(imageUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap bitmap = BitmapFactory.decodeStream(input);
//            return bitmap;
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//
//        }
        new JSONAsyncTask().execute(imageUrl);
    }
    public  class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
        Dialog dialog;
        public JSONAsyncTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            if(isSwipeRefresh == false) {
//                swipeRefreshLayout.setRefreshing(true);
//            }

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
                return true;

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;

            }

        }
        protected void onPostExecute(Boolean result) {

            sendNotification("message", "heading",bitmap);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
