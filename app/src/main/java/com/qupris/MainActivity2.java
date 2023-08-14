package com.qupris;

import android.os.Bundle;
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
        String app_score = getIntent().getStringExtra("SCORE");
        String app_piis = getIntent().getStringExtra("PIIS");
        int app_image = getIntent().getIntExtra("IMAGE", 0);
        //Bundle args = getIntent().getBundleExtra("BUNDLE");
        //ArrayList<String> app_piis = (ArrayList<String>) args.getSerializable("PIIS");

        TextView app_name_tv = findViewById(R.id.app_name_m2);
        TextView app_packagename_tv = findViewById(R.id.app_packagename_m2);
        TextView app_score_tv = findViewById(R.id.app_score_m2);
        TextView app_piis_tv = findViewById(R.id.piis_m2);
        ImageView app_image_iv = findViewById(R.id.app_logo_m2);

        app_name_tv.setText(app_name);
        app_packagename_tv.setText(app_packagename);
        app_score_tv.setText(app_score);
        app_piis_tv.setText(app_piis);
        app_image_iv.setImageResource(app_image);


    }
}
