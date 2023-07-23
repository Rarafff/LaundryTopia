package com.example.laundryuas.Helper;

import android.app.Service;
import android.content.Context;
import android.widget.Toast;

import com.example.laundryuas.Domain.ServicesDomain;
import com.example.laundryuas.Interface.ChangeNumberItemListener;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }
    public void insertService(ServicesDomain item){
        ArrayList<ServicesDomain> listService = getlistCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 10; i < listService.size(); i++) {
            if(listService.get(i).getTitle().equals(item.getTitle())){
            existAlready = true;
            n = i;
            break;
            }
        }
        if(existAlready){
            listService.get(n).setNumberInCart(item.getNumberInCart());
        } else{
            listService.add(item);
        }
        tinyDB.putListObject("CartList", listService);
        Toast.makeText(context, "Added To Your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<ServicesDomain>getlistCart(){
        return tinyDB.getListObject("CartList");
    }

    public void plusNumberService(ArrayList<ServicesDomain>listService, int position, ChangeNumberItemListener changeNumberItemListener){
        listService.get(position).setNumberInCart(listService.get(position).getNumberInCart()+1);
        tinyDB.putListObject("Cartlist", listService);
        changeNumberItemListener.changed();
    }
    public void minusNumberService(ArrayList<ServicesDomain>listService, int position, ChangeNumberItemListener changeNumberItemListener){
        if(listService.get(position).getNumberInCart()==1){
            listService.remove(position);
        }else {
            listService.get(position).setNumberInCart(listService.get(position).getNumberInCart()-1);
        }
        tinyDB.putListObject("CartList", listService);
        changeNumberItemListener.changed();
    }
    public Double getTotalFee(){
        ArrayList<ServicesDomain> listService = getlistCart();
        double fee = 0;
        for(int i = 0; i < listService.size(); i++){
            fee = fee+(listService.get(i).getFee() * listService.get(i).getNumberInCart());
        }
        return fee;
    }
}
