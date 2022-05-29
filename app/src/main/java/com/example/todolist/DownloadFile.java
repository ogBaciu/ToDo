package com.example.todolist;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.util.Objects;

public class DownloadFile extends AsyncTask<String, String, String> {
    private Context mContext;

    public DownloadFile(Context context) {
        this.mContext = context;
    }
    /**
     * Before starting background thread
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("Starting download");
    }
    /**
     * Downloading file in background thread
     * */
    @Override
    protected String doInBackground(String... f_url) {
        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            Log.d("####### DWLD", "Starting Downloaded " + path);

            File file = new File(path, "sample_image.jpg");
            try {
                Log.e("download", "Try statement");
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://images.all-free-download.com/images/graphicwebp/pineapple_splashes_into_the_water_605709.webp"))
                        .setTitle("sample_image")// Title of the Download Notification
                        .setDescription("Downloading")// Description of the Download Notification
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                        .setDestinationUri(Uri.fromFile(file));
                DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(mContext.DOWNLOAD_SERVICE);
                long downloadID = Objects.requireNonNull(downloadManager).enqueue(request);
                Log.d("####### DWLD", " Download Successful");
            } catch (IllegalArgumentException e) {
                Log.e("download", "downnload error "+e);
            }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            Log.d("####### DWLD", " Error");
        }

        return null;
    }

    /**
     * After completing background task
     * **/
    @Override
    protected void onPostExecute(String file_url) {
        Log.d("####### DWLD", " END Download");
    }

}
