package com.example.devashishsharma.charliesearchv11;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devashishsharma.charliesearchv11.Data.Query;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String status;
    Button btnCheckConn;
    //TextView ConnLabel;
    EditText editText;
    public int i=1;
    private static String conn_url = "http://www.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //ConnLabel = (TextView)findViewById(R.id.ConnStatus);
        btnCheckConn = (Button)findViewById(R.id.Conn_button);
        btnCheckConn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new CheckConnection().execute(conn_url);
            }
        });
        editText = (EditText)findViewById(R.id.searchText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String timeStamp(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyy-hh-mm-ss");
        String format = simpleDateFormat.format(new Date());
        return format;
    }

    public class CheckConnection extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... c_url){

            try{
                URL url = new URL(c_url[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if(connection.getResponseCode()!=-1){
                   return status = "OK";
                }
                else{
                    return status = "NOK";
                }
            }
            catch(Exception e){
                Log.e("Error: ", e.getMessage());
                status = "NOK";
            }
         return status;
        }

        @Override
        protected void onPostExecute(String code){
            if(status.equals("OK")){
                //ConnLabel.setText("Connected");
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                String term = editText.getText().toString();
                intent.putExtra(SearchManager.QUERY,term);
                startActivity(intent);
            }else {
                //ConnLabel.setText("Not Connected");
                String ts = timeStamp();
                String searchText = editText.getText().toString();
                DbHandler dbHandler = new DbHandler(getApplicationContext());
                dbHandler.addQuery(new Query(i,searchText,ts));
                Log.d("Insert: ","Inserting...");
                Context context = getApplicationContext();
                CharSequence text = "Net Nhi Hai Bad Mei Aana";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();i++;
            }
        }
    }
}
