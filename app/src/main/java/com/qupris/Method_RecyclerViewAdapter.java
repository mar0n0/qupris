package com.qupris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class PII_RecyclerViewAdapter extends RecyclerView.Adapter<PII_RecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<AppModel> appModels;

    public PII_RecyclerViewAdapter(Context context, ArrayList<AppModel> appModels, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.appModels = appModels;
    }

    @NonNull
    @Override
    // This is where you inflate the layout (Giving a look to our rows)
    public PII_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row, parent, false);
        return new PII_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    // The number of items you want to display
    public int getItemCount() {
        return appModels.size();
    }


    @Override
    // Assigning values to each row when they are going back to the screen
    public void onBindViewHolder(@NonNull PII_RecyclerViewAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

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

    }



    // Creating variables with the rows
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView app_image;
        TextView tv_name, tv_packagename, tv_version, tv_score;
        FloatingActionButton analyzeButton;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            app_image = itemView.findViewById(R.id.imageView); //Image
            tv_name = itemView.findViewById(R.id.textView); //Name
            tv_packagename = itemView.findViewById(R.id.textView2); //PackageName
            tv_version = itemView.findViewById(R.id.textView4); //Version
            tv_score = itemView.findViewById(R.id.textView3); //Score
            analyzeButton = itemView.findViewById(R.id.analyzeButton);

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
