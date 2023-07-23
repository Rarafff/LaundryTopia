package com.example.laundryuas.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.laundryuas.Domain.ServicesDomain;
import com.example.laundryuas.Helper.ManagementCart;
import com.example.laundryuas.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView btnAddToCart;
    private TextView txtTitle, txtFee, txtDesc, txtNumberOrder;
    private ImageView btnPlus, btnMinus, servicePic;
    private ServicesDomain object;
    int numberOrder = 1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        managementCart = new ManagementCart(this);
        initView();
        getBundle();

    }

    private void getBundle() {
        object = (ServicesDomain) getIntent().getSerializableExtra("object");

        int drawableResourceId = this.getResources().getIdentifier(object.getPic(), "drawable", this.getOpPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(servicePic);

        txtTitle.setText(object.getTitle());
        txtFee.setText("$" + object.getFee());
        txtDesc.setText(object.getDesc());
        txtNumberOrder.setText(String.valueOf(numberOrder));

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder + 1;
                txtNumberOrder.setText(String.valueOf(numberOrder));
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberOrder > 1){
                    numberOrder = numberOrder - 1;
                }
                txtNumberOrder.setText(String.valueOf(numberOrder));
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object.setNumberInCart(numberOrder);
                managementCart.insertService(object);
            }
        });

    }

    private void initView() {
        btnAddToCart = findViewById(R.id.btnAddToCart);
        txtTitle = findViewById(R.id.txtTitle);
        txtFee = findViewById(R.id.txtPrice);
        txtDesc = findViewById(R.id.txtDesc);
        txtNumberOrder = findViewById(R.id.txtNumberOrder);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        servicePic = findViewById(R.id.servicePic);
    }
}