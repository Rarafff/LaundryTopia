package com.example.laundryuas.Adaptor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.laundryuas.Activity.ShowDetailActivity;
import com.example.laundryuas.Domain.ServicesDomain;
import com.example.laundryuas.R;

import java.util.ArrayList;

public class ServicesAdaptor extends RecyclerView.Adapter<ServicesAdaptor.ViewHolder> {
    ArrayList<ServicesDomain> LaundryServices;

    public ServicesAdaptor(ArrayList<ServicesDomain> LaundryServices) {
        this.LaundryServices = LaundryServices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_services,parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdaptor.ViewHolder holder, int position) {
        holder.title.setText(LaundryServices.get(position).getTitle());
        holder.fee.setText(String.valueOf(LaundryServices.get(position).getFee()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(LaundryServices.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", LaundryServices.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return LaundryServices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, fee;
        ImageView pic;
        TextView btnAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            fee = itemView.findViewById(R.id.fee);
            pic = itemView.findViewById(R.id.pic);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}
