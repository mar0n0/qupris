package com.qupris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class App_RecyclerViewAdapter extends RecyclerView.Adapter<App_RecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<AppModel> appModels;

    public App_RecyclerViewAdapter(Context context, ArrayList<AppModel> appModels, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.appModels = appModels;
    }

    @NonNull
    @Override
    // This is where you inflate the layout (Giving a look to our rows)
    public App_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row, parent, false);
        return new App_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    // The number of items you want to display
    public int getItemCount() {
        return appModels.size();
    }


    @Override
    // Assigning values to each row when they are going back to the screen
    public void onBindViewHolder(@NonNull App_RecyclerViewAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_name.setText(appModels.get(position).getName()); //Name
        holder.tv_packagename.setText(appModels.get(position).getPackage_name()); //Package Name
        holder.tv_version.setText(String.valueOf(appModels.get(position).getVersion())); //Version
        holder.app_image.setImageDrawable(appModels.get(position).getImage()); //Image

        if (appModels.get(position).isAnalyzed()){
            holder.analyzeButton.setVisibility(View.GONE);
            holder.tv_score.setVisibility(View.VISIBLE);
            holder.tv_score.setText(String.valueOf(appModels.get(position).getScore())); //Score
        } else{
            holder.analyzeButton.setVisibility(View.VISIBLE);
        }

        if (appModels.get(position).isSystemApp()){
            holder.tv_system_app.setText("System App");
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.purple_200));
        } else{
            holder.tv_system_app.setText("User App");
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.purple_300));
        }

    }



    // Creating variables with the rows
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView app_image;
        TextView tv_name, tv_packagename, tv_version, tv_score, tv_system_app;
        FloatingActionButton analyzeButton;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            app_image = itemView.findViewById(R.id.imageView); //Image
            tv_name = itemView.findViewById(R.id.textView); //Name
            tv_packagename = itemView.findViewById(R.id.textView2); //PackageName
            tv_version = itemView.findViewById(R.id.textView4); //Version
            tv_score = itemView.findViewById(R.id.textView3); //Score
            tv_system_app = itemView.findViewById(R.id.textView5); //System App
            analyzeButton = itemView.findViewById(R.id.analyzeButton); //Analyze Button

            // App analyze button
            analyzeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            try {
                                recyclerViewInterface.onAnalyseButtonClick(pos);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

            // App information button
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
