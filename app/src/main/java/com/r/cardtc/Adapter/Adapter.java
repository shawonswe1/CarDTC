package com.r.cardtc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.r.cardtc.DetailsActivity;
import com.r.cardtc.Model.GetCar;
import com.r.cardtc.Model.model;
import com.r.cardtc.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    Context context;
    List<GetCar> getCarList;
public Adapter(){

}
    public Adapter(Context context, List<GetCar> getCarList) {
        this.context = context;
        this.getCarList = getCarList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ro_data,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final GetCar getCar = getCarList.get(position);
        holder.textViewName.setText(getCar.getName());
        String Url = "http://obderrorcode.com/android";
        Glide.with(context).load(Url+getCar.getImage()).into(holder.imageView);
        Log.e("Link",Url+getCar.getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailsActivity.class);
                intent.putExtra("id",getCar.getUid());
                //Send Image Url
                intent.putExtra("image",getCar.getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getCarList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewName;
        TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            textViewName = itemView.findViewById(R.id.textName);

        }
    }
}
