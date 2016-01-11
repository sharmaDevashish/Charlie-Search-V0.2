package com.example.devashishsharma.charliesearchv11;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.devashishsharma.charliesearchv11.Data.Query;

import java.util.List;

/**
 * Created by devashish.sharma on 1/11/2016.
 */
public class NetworkChangeReciever extends BroadcastReceiver{

    int mId=1;
    String conn_url = "https://www.google.com";
    @Override
    public void onRecieve(Context context,Intent intent){
        DbHandler dbHandler = new DbHandler(context);
        String status = new ConnectionDetector().doInBackground(conn_url);
        if(status.equals("OK")){
            Log.d("Reading: ", "Reading Query");
            List<Query> queryList = DbHandler.getAllquery();
            for(Query query:queryList){
                String log = "Id: "+ query.getId()+" ,Query: "+query.getQuery()+" ,Time: "+query.getTime();
                Log.d("Query: ",log);
            }
            Log.d("Reading: ", "Reading");
            int count = DbHandler.getQueryCount();
            if(count>0) {
                for (int i=count;i>0;i--) {
                    Query TheQuery = DbHandler.getQuery(i);
                    String log = "Id: " + TheQuery.getId() + ",Query:" + TheQuery.getQuery() + ",Time: " + TheQuery.getTime();
                    Log.d("Query: ", log);
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                            .setVibrate(new long[]{1000, 1000, 1000})
                            .setLights(Color.GREEN, 3000, 3000)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Charlie Search")
                            .setContentText("You searched for " + TheQuery.query)
                            .setSubText("Here are Your Results")
                            .setAutoCancel(true)
                            .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), 0));
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent NotiIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                    String term = TheQuery.query;
                    NotiIntent.putExtra(SearchManager.QUERY, term);
                    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
                    taskStackBuilder.addParentStack(MainActivity.class);
                    taskStackBuilder.addNextIntent(NotiIntent);
                    PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(i, PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.setContentIntent(pendingIntent);
                    DbHandler.deleteQuery(count);
                    notificationManager.notify(mId, mBuilder.build());
                    mId++;
                }
        }
    }
}
