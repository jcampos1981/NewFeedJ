package com.crdesigns.newfeedj;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;

public class VideoActivity extends AppCompatActivity {
    Uri uri;
    String stringUrl;
    String stringUrlSource;
    String Points;
    String idUsr;
    int code;
    VideoView simpleVideoView;

    final MySQLiteHelper db = new MySQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Bundle extras = getIntent().getExtras();
        //idUsr = extras.getString("idUsr");
        //Points = extras.getString("Points");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        simpleVideoView = (VideoView) findViewById(R.id.simpleVideoView); // initiate a video view
        uri = Uri.parse("https://player.vimeo.com/external/163914755.sd.mp4?s=b98042d47eca319e213e11087408bf73008c4178&profile_id=164");

        //First try to read video from edge, if not, read original source
        List<String> lables = db.getVideo();
        if(lables.size() >= 1) {
            String[] separated = lables.get(0).split("\\|");
            stringUrl = separated[1];
            stringUrlSource = separated[0];

            //progDialog = ProgressDialog.show(getApplicationContext(), "Please wait ...", "Retrieving data ...", true);
            AsyncTaskRunner postReq2 = new AsyncTaskRunner();
            postReq2.execute("start");
        }

        simpleVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                //progDialog.dismiss();
            }
        });

        simpleVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //now mark as viewed
                db.updateViewed(idUsr,String.valueOf(uri));
                onBackPressed();
            }
        });
    }

    private class AsyncTaskRunner extends AsyncTask<String,String,String> {
        //private ProgressDialog dialog = new ProgressDialog(getContext());
        @Override
        protected String doInBackground(String... params) {
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();
            try {
                URL url = new URL(stringUrl);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                code = connection.getResponseCode(); // show code result :)

                InputStream is;
                if (connection.getResponseCode() == HTTP_OK) {
                    is = connection.getInputStream();
                    uri = Uri.parse(stringUrl);//edge uri
                } else {
                    is = connection.getErrorStream();
                    uri = Uri.parse(stringUrlSource);//edge uri
                }
                return "";
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
                return "";
            }
        }
        @Override
        protected void onPostExecute(String s) {
            try{
                super.onPostExecute(s);
                simpleVideoView.setVideoURI(uri);
                simpleVideoView.start();
            }
            catch (Exception e){
                Log.v("ErrorAPP",e.toString());
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }
}
