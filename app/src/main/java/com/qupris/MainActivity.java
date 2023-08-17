package com.qupris;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<AppModel> app_models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.app_recyclerview);

        try {
            setUpAppModels();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        App_RecyclerViewAdapter adapter = new App_RecyclerViewAdapter(this, app_models, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Setup the app models information
    // TODO: Add methods that will grab device apps and populate the app_models variable to create the recyclerview
    private void setUpAppModels() throws PackageManager.NameNotFoundException {
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo appInfo : installedApps) {

            String packageName = appInfo.packageName; //Package Name

            if(!isSystemPackage(appInfo) && !Objects.equals(packageName, "com.qupris")) { //Verify if it is a system app or my app

                String appName = appInfo.loadLabel(packageManager).toString(); //Name

                Resources res = packageManager.getResourcesForApplication(appInfo);
                Drawable appIcon = res.getDrawableForDensity(appInfo.icon, DisplayMetrics.DENSITY_XXXHIGH, null); //Image

                PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
                int version = packageInfo.versionCode; //VersionCode

                int score = getScoreFromContentProvider(packageName, version); //score
                String pii = ""; //pii
                boolean isAnalyzed = false; //analyzed

                if(score >= 0){
                    pii = getPIIFromContentProvider(packageName, version);
                    isAnalyzed = true;
                }

                AppModel app = new AppModel(appName, packageName, version, score, appIcon, pii, isAnalyzed);
                app_models.add(app);
            }
        }


    }

    // Get Score from content provider
    @SuppressLint("Range")
    private int getScoreFromContentProvider(String packagename, int version){

        String selection = "packagename = ? AND version = ?";
        String[] selectionArgs = new String[] {packagename, String.valueOf(version)};

        Cursor cursor = getContentResolver().query(AppContentProvider.CONTENT_URI, null, selection, selectionArgs, null);

        int score = -1;
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                if(Objects.equals(cursor.getString(cursor.getColumnIndex("packagename")), packagename) &&
                        cursor.getInt(cursor.getColumnIndex("version")) == version){
                    score = cursor.getInt(cursor.getColumnIndex("score"));
                    break;
                }
                cursor.moveToNext();
            }
        }

        return score;
    }

    // Get PIIs from content provider
    @SuppressLint("Range")
    private String getPIIFromContentProvider(String packagename, int version){
        String selection = "packagename = ? AND version = ?";
        String[] selectionArgs = new String[] {packagename, String.valueOf(version)};

        Cursor cursor = getContentResolver().query(AppContentProvider.CONTENT_URI, null, selection, selectionArgs, null);

        String pii = "";
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                if(Objects.equals(cursor.getString(cursor.getColumnIndex("packagename")), packagename) &&
                        cursor.getInt(cursor.getColumnIndex("version")) == version){
                    pii = cursor.getString(cursor.getColumnIndex("piis"));
                    break;
                }
                cursor.moveToNext();
            }
        }

        return pii;
    }

    // Method to verify if app is a system app
    private boolean isSystemPackage(ApplicationInfo appInfo) {
        return ((appInfo.flags & (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP | ApplicationInfo.FLAG_SYSTEM)) > 0);
    }

    // Go to App Activity with arguments
    // TODO: Define what information to shown on app activity
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("NAME", app_models.get(position).getName());
        intent.putExtra("PACKAGE_NAME", app_models.get(position).getPackage_name());
        intent.putExtra("VERSION", app_models.get(position).getVersion());
        intent.putExtra("SCORE", app_models.get(position).getScore());
        intent.putExtra("PIIS", app_models.get(position).getPiis());

        startActivity(intent);
    }


    // Create method to make post request on clicking in button to analyze
    @Override
    public void onAnalyseButtonClick(int position) {

        String package_name = app_models.get(position).getPackage_name();
        int version = app_models.get(position).getVersion();

        //TODO: Make post request
        Toast.makeText(this, "Making Post Request", Toast.LENGTH_SHORT).show();

        String jsonString = "";
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("packagename", app_models.get(position).getPackage_name());
            jsonObject.put("packageversion", String.valueOf(app_models.get(position).getVersion()));
            jsonString = jsonObject.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //PostDataAsync postData = new PostDataAsync(app_models.get(position));
        //postData.execute("http://127.0.0.1:9000/analysis", jsonString);
        int score = 0;
        String piis = "";
        saveScoreCP(app_models.get(position), score, piis);

        //TODO: Save Response to content provider
        Toast.makeText(this, "Saving information to content provider", Toast.LENGTH_SHORT).show();;


    }

    // Save data in content provider
    @SuppressLint("Range")
    private void saveScoreCP(AppModel app, int score, String piis){


        String selection = "packagename = ? AND version = ?";
        String[] selectionArgs = new String[] {app.getPackage_name(), String.valueOf(app.getVersion())};

        Cursor cursor = getContentResolver().query(AppContentProvider.CONTENT_URI, null, selection, selectionArgs, null);

        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                if(Objects.equals(cursor.getString(cursor.getColumnIndex("packagename")), app.getPackage_name()) &&
                        cursor.getInt(cursor.getColumnIndex("version")) == app.getVersion()){
                    Toast.makeText(getBaseContext(), "Already have a an app!", Toast.LENGTH_LONG).show();
                    return;
                }
                cursor.moveToNext();
            }
        }

        ContentValues values = new ContentValues();
        values.put(AppContentProvider.PACKAGENAME, app.getPackage_name());
        values.put(AppContentProvider.VERSION, app.getVersion());
        values.put(AppContentProvider.NAME, app.getName());
        values.put(AppContentProvider.SCORE, score);
        values.put(AppContentProvider.PIIS, piis);

        Uri uri = getContentResolver().insert(AppContentProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), "App saved!", Toast.LENGTH_LONG).show();
    }

    class PostDataAsync extends AsyncTask<String, Void, String> {

        public AppModel app;

        public PostDataAsync(AppModel app) {
            this.app = app;
        }

        @Override
        protected String doInBackground(String... params) {
            return NetworkUtilities.postData(params[0], params[1]);
        }
        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject obj = new JSONObject(result);
                Log.d("Response: ", obj.toString());

                int score = Integer.valueOf(obj.getString("score"));
                String piis = obj.getString("piis");

                Log.d("Score: ", String.valueOf(score));
                Log.d("Piis: ", piis);

                saveScoreCP(app, score, piis); //Save information in content provider
            } catch (JSONException e) {
                e.printStackTrace();
            }




        }
    }
}


