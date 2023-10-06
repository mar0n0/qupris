package com.qupris;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    ArrayList<MethodModel> method_models = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RecyclerView recyclerView = findViewById(R.id.method_recyclerview);

        String app_name = getIntent().getStringExtra("NAME");
        String app_packagename = getIntent().getStringExtra("PACKAGE_NAME");
        int app_score = getIntent().getIntExtra("SCORE", 0);
        String app_piis = getIntent().getStringExtra("PIIS");
        int version = getIntent().getIntExtra("VERSION", 0);
        Boolean system_app = getIntent().getBooleanExtra("SYSTEM_APP", true);


        // IMAGE
        PackageManager packageManager = getPackageManager();
        Drawable appIcon = null;
        try {
            appIcon = packageManager.getApplicationIcon(app_packagename);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ConstraintLayout constraint_layout_activity_2 = findViewById(R.id.constraint_layout_activity_2);
        TextView app_name_tv = findViewById(R.id.app_name_m2);
        TextView app_packagename_tv = findViewById(R.id.app_packagename_m2);
        TextView app_score_tv = findViewById(R.id.app_score_m2);
        TextView app_version_tv = findViewById(R.id.version_m2);
        ImageView app_image_iv = findViewById(R.id.app_logo_m2);


        app_name_tv.setText(app_name); //NAME
        app_packagename_tv.setText(app_packagename); //PACKAGENAME
        app_score_tv.setText(String.valueOf(app_score)); //SCORE
        app_version_tv.setText(String.valueOf(version)); //VERSION
        app_image_iv.setImageDrawable(appIcon); //Image

        if(system_app){
            constraint_layout_activity_2.setBackgroundColor(getResources().getColor(R.color.purple_200));
        }else{
            constraint_layout_activity_2.setBackgroundColor(getResources().getColor(R.color.purple_300));
        }


        if (!app_piis.equals("")){
            TextView no_piis_tv = findViewById(R.id.no_piis);
            no_piis_tv.setVisibility(View.GONE);
            setupMethoModels(app_piis);

            Method_RecyclerViewAdapter adapter = new Method_RecyclerViewAdapter(this, method_models);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }else{
            TextView no_piis_tv = findViewById(R.id.no_piis);
            no_piis_tv.setText("App not analyzed!");
            no_piis_tv.setVisibility(View.VISIBLE);


        }


    }

    private void setupMethoModels(String app_piis){

        String[] commandValuePairs = app_piis.split(";");


        String[] commands = new String[commandValuePairs.length];
        String[] values = new String[commandValuePairs.length];

        for (int i = 0; i < commandValuePairs.length; i++) {
            String[] parts = commandValuePairs[i].split(":");
            commands[i] = parts[0];
            values[i] = parts[1];
        }

        Log.d(TAG, commands.toString());
        Log.d(TAG, values.toString());


        //String[] method_array = getResources().getStringArray(R.array.teste_method);
        //String[] array_pii = getResources().getStringArray(R.array.teste_pii);
        for(int i = 0; i < commands.length; i++){
            MethodModel methodModel = new MethodModel(commands[i], values[i]);
            method_models.add(methodModel);
        }
    }
}
