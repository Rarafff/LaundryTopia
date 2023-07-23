package com.example.laundryuas.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.laundryuas.Activity.CartListActivity;
import com.example.laundryuas.Domain.ServicesDomain;
import com.example.laundryuas.Helper.ManagementCart;
import com.example.laundryuas.Interface.ChangeNumberItemListener;
import com.example.laundryuas.R;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<ServicesDomain> servicesDomains;
    private ManagementCart managementCart;
    private ChangeNumberItemListener changeNumberItemListener;

    public CartListAdapter(ArrayList<ServicesDomain> servicesDomains, Context context, ChangeNumberItemListener changeNumberItemListener) {
        this.servicesDomains = servicesDomains;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemListener = changeNumberItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate =  LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);


        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(servicesDomains.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(servicesDomains.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round((servicesDomains.get(position).getNumberInCart()* servicesDomains.get(position).getFee())*100)/100));
        holder.num.setText(String.valueOf(servicesDomains.get(position).getNumberInCart()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(servicesDomains.get(position).getPic()
                ,"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.plusNumberService(servicesDomains,
                        holder.getAdapterPosition(),
                        new ChangeNumberItemListener() {
                            @Override
                            public void changed() {
                                notifyDataSetChanged();
                                changeNumberItemListener.changed();

                            }
                        });
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.minusNumberService(servicesDomains,
                        holder.getAdapterPosition(),
                        new ChangeNumberItemListener() {
                            @Override
                            public void changed() {
                                notifyDataSetChanged();
                                changeNumberItemListener.changed();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return servicesDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCart);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.txtNumberItem);
            plusItem = itemView.findViewById(R.id.btnPlusCart);
            minusItem = itemView.findViewById(R.id.btnMinusCart);
        }
    }
}
