package com.qupris;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String app_name = getIntent().getStringExtra("NAME");
        String app_packagename = getIntent().getStringExtra("PACKAGE_NAME");
        int app_score = getIntent().getIntExtra("SCORE", 0);
        String app_piis = getIntent().getStringExtra("PIIS");
        int version = getIntent().getIntExtra("VERSION", 0);


        // IMAGE
        PackageManager packageManager = getPackageManager();
        Drawable appIcon = null;
        try {
            appIcon = packageManager.getApplicationIcon(app_packagename);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //int app_image = getIntent().getIntExtra("IMAGE", 0);
        //Bundle args = getIntent().getBundleExtra("BUNDLE");
        //ArrayList<String> app_piis = (ArrayList<String>) args.getSerializable("PIIS");

        TextView app_name_tv = findViewById(R.id.app_name_m2);
        TextView app_packagename_tv = findViewById(R.id.app_packagename_m2);
        TextView app_score_tv = findViewById(R.id.app_score_m2);
        TextView app_version_tv = findViewById(R.id.version_m2);
        TextView app_piis_tv = findViewById(R.id.piis_m2);
        ImageView app_image_iv = findViewById(R.id.app_logo_m2);

        app_name_tv.setText(app_name); //NAME
        app_packagename_tv.setText(app_packagename); //PACKAGENAME
        app_score_tv.setText(String.valueOf(app_score)); //SCORE
        app_version_tv.setText(String.valueOf(version)); //VERSION
        app_piis_tv.setText(app_piis); //PIIS
        app_image_iv.setImageDrawable(appIcon); //Image


    }
}
