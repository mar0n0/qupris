package com.qupris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<AppModel> app_models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.app_recyclerview);

        setUpAppModels();

        App_RecyclerViewAdapter adapter = new App_RecyclerViewAdapter(this, app_models, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Setup the app models information
    private void setUpAppModels(){
        String[] app_names = getResources().getStringArray(R.array.test_rows_app_names_example);
        String[] app_packagenames = getResources().getStringArray(R.array.test_rows_app_packagename_example);
        String[] app_piis = getResources().getStringArray(R.array.test_rows_app_piis_example);
        int[] app_scores = getResources().getIntArray(R.array.test_rows_app_score_example);
        //ArrayList<String> app_piis = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.test_rows_app_piis_example)));

        for (int i = 0; i < app_names.length; i++){
            AppModel app = new AppModel(app_names[i], app_packagenames[i], app_scores[i], R.drawable.ic_launcher_foreground, app_piis[i]);
            app_models.add(app);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("NAME", app_models.get(position).getName());
        intent.putExtra("PACKAGE_NAME", app_models.get(position).getPackage_name());
        intent.putExtra("SCORE", app_models.get(position).getScore());
        intent.putExtra("PIIS", app_models.get(position).getPiis());
        intent.putExtra("IMAGE", app_models.get(position).getImage());
        //Bundle args = new Bundle();
        //args.putSerializable("PIIS", app_models.get(position).getPiis());
        //intent.putExtra("BUNDLE", args);

        startActivity(intent);
    }

    // Create method to make post request on clicking in button to analyze

    // Create method to grab the app analysis that already are in the content provider
}