package kr.hs.dimigo.dudgns0507.hongikbook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pyh42 on 2016-11-24.
 */

public class GetImageFromUrl {
    private final static String TAG = "GetImageFromUrl";
    public AsyncResponse delegate = null;

    public void getData(String url){
        class GetBitmap extends AsyncTask<String, Void, Bitmap> {
            @Override
            protected Bitmap doInBackground(String... params) {
                String uri = params[0];

                if(uri.equals(""))
                    return null;

                Bitmap imgBitmap = null;
                HttpURLConnection conn = null;
                BufferedInputStream bis = null;

                try
                {
                    URL url = new URL(uri);
                    conn = (HttpURLConnection)url.openConnection();
                    conn.connect();

                    int nSize = conn.getContentLength();
                    bis = new BufferedInputStream(conn.getInputStream(), nSize);
                    imgBitmap = BitmapFactory.decodeStream(bis);
                }
                catch (Exception e){
                    e.printStackTrace();
                } finally{
                    if(bis != null) {
                        try {bis.close();} catch (IOException e) {}
                    }
                    if(conn != null ) {
                        conn.disconnect();
                    }
                }

                return imgBitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if(bitmap != null)
                    delegate.processFinish(bitmap);
                else
                    delegate.processFinish(null);
            }
        }

        GetBitmap getBitmap = new GetBitmap();
        getBitmap.execute(url);
    }
}
