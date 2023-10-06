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

public class Method_RecyclerViewAdapter extends RecyclerView.Adapter<Method_RecyclerViewAdapter.MyViewHolder> {


    Context context;
    ArrayList<MethodModel> methodModels;

    public Method_RecyclerViewAdapter(Context context, ArrayList<MethodModel> methodModels) {
        this.context = context;
        this.methodModels = methodModels;
    }

    @NonNull
    @Override
    // This is where you inflate the layout (Giving a look to our rows)
    public Method_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.method_recyclerview_row, parent, false);
        return new Method_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    // The number of items you want to display
    public int getItemCount() {
        return methodModels.size();
    }


    @Override
    // Assigning values to each row when they are going back to the screen
    public void onBindViewHolder(@NonNull Method_RecyclerViewAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_method.setText(methodModels.get(position).getMethod()); //Method
        holder.tv_pii.setText(methodModels.get(position).getPii()); //PII

    }


    // Creating variables with the rows
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_method, tv_pii;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_method = itemView.findViewById(R.id.method); //Image
            tv_pii = itemView.findViewById(R.id.pii); //Name

        }
    }
}
