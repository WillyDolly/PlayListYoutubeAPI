package com.popland.pop.playlistyoutubeapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String API_key = "AIzaSyAyrdiDf0tLplPdxjioYiIv_ZEw42eARyc";
    String playlistID ="PLnV5gfO-gDDYbgffOLe76PZSqBsMjfiNa";
    ListView lv;
    ArrayList<VideoList> arrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv =(ListView)findViewById(R.id.LV);
        arrl = new ArrayList<VideoList>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new DocJSON().execute("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + playlistID +"$maxResult=50"+ "&key=" + API_key);
            }
        });
    }
    class DocJSON extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String title = "";
            String url = "";
            String videoId ="";
            try {
                JSONObject object = new JSONObject(s);
                JSONArray arrayItems = object.getJSONArray("items");
                for(int i=0;i<arrayItems.length();i++){
                    JSONObject item = arrayItems.getJSONObject(i);
                    JSONObject snippet = item.getJSONObject("snippet");
                    title = snippet.getString("title");
                    JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                    JSONObject hinh = thumbnails.getJSONObject("default");
                    url = hinh.getString("url");
                    JSONObject resourceId = snippet.getJSONObject("resourceId");
                    videoId = resourceId.getString("videoId");
                    arrl.add(new VideoList(url,title,videoId));
                }
                CustomListAdapter adapter = new CustomListAdapter(MainActivity.this,R.layout.custom_layout,arrl);
                lv.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private static String docNoiDung_Tu_URL(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }
 }
