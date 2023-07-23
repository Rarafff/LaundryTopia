package com.example.laundryuas.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundryuas.Adaptor.CartListAdapter;
import com.example.laundryuas.Helper.ManagementCart;
import com.example.laundryuas.Interface.ChangeNumberItemListener;
import com.example.laundryuas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView txtTotalFee, txtTax, txtDelivery, txtTotal, txtEmpty;
    private double tax;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managementCart = new ManagementCart(this);
        
        initView();
        initList();
        CalculateCart();
        bottomNavigation();
    }

    private void bottomNavigation(){
        LinearLayout btnCart = findViewById(R.id.btnCart);
        LinearLayout btnHome = findViewById(R.id.btnHome);
        LinearLayout btnProfile = findViewById(R.id.btnProfile);
        LinearLayout btnHelp = findViewById(R.id.btnHelp);
        LinearLayout btnSoon = findViewById(R.id.btnSoon);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, ProfileActivity.class));
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, SupportActivity.class));
            }
        });
        btnSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CartListActivity.this, "Coming Soon!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerView);
        txtTotalFee = findViewById(R.id.txtTotalFee);
        txtTax = findViewById(R.id.txtTax);
        txtDelivery = findViewById(R.id.txtDelivery);
        txtTotal = findViewById(R.id.txtTotal);
        txtEmpty = findViewById(R.id.txtEmpty);
        scrollView = findViewById(R.id.scrollView3);
        recyclerViewList=findViewById(R.id.cartView);
    }
    private void initList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getlistCart(), this, new ChangeNumberItemListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });
        recyclerViewList.setAdapter(adapter);
        if(managementCart.getlistCart().isEmpty()){
            txtEmpty.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }else {
            txtEmpty.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }
    private void CalculateCart(){
        double percentTax = 0.20;
        double delivery = 0;

        tax = Math.round((managementCart.getTotalFee()*percentTax)*100) / 100;
        double total = Math.round((managementCart.getTotalFee()+tax+delivery)*100) / 100;
        double itemTotal = Math.round(managementCart.getTotalFee()*100) / 100;

        txtTotalFee.setText("$" + itemTotal);
        txtTax.setText("$"+tax);
        txtDelivery.setText("$"+delivery);
        txtTotal.setText("$"+total);
    }
}