package com.qupris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    // Assigning values to each row when they are going back to the screen
    public void onBindViewHolder(@NonNull App_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tv_name.setText(appModels.get(position).getName());
        holder.tv_packagename.setText(appModels.get(position).getPackage_name());
        holder.tv_score.setText(String.valueOf(appModels.get(position).getScore()));
        holder.app_image.setImageResource(appModels.get(position).getImage());
    }

    @Override
    // The number of items you want to display
    public int getItemCount() {
        return appModels.size();
    }

    // Creating variables with the rows
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView app_image;
        TextView tv_name, tv_packagename, tv_score;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            app_image = itemView.findViewById(R.id.imageView);
            tv_name = itemView.findViewById(R.id.textView);
            tv_packagename = itemView.findViewById(R.id.textView2);
            tv_score = itemView.findViewById(R.id.textView3);

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
