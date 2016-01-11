package com.example.devashishsharma.charliesearchv11;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by devashish.sharma on 1/11/2016.
 */
public class ConnectionDetector extends AsyncTask<String, String, String> {

    String status;

    @Override
    protected String doInBackground(String...c_url){
        try{
            URL url = new URL(c_url[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.connect();
            if(connection.getResponseCode()!=-1){
                return status = "OK";
            }
            else{
                return status = "NOK";
            }
        }
        catch (Exception e){
            Log.e("Error: ",e.getMessage());
            return status = "NOK";
        }
    }

    @Override
    protected void onPostExecute(String code){
        if(status.equals("OK")){
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            String term =
        }
    }
}
