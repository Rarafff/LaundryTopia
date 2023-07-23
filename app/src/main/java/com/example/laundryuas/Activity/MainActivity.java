package com.example.laundryuas.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundryuas.Adaptor.CategoryAdaptor;

import com.example.laundryuas.Adaptor.ServicesAdaptor;
import com.example.laundryuas.Domain.CategoryDomain;

import com.example.laundryuas.Domain.ServicesDomain;
import com.example.laundryuas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewServicesList;
    private FirebaseAuth firebaseAuth;
    private TextView tvUsername;

    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();
        recycleViewServices();
        bottomNavigation();

        firebaseAuth = FirebaseAuth.getInstance();
        tvUsername = findViewById(R.id.tvUsername);
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    // Retrieving Data from firebase
                    String name = "" + dataSnapshot1.child("name").getValue(String.class);
                    // setting data to our text view
                    tvUsername.setText(name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void bottomNavigation(){
        LinearLayout btnCart = findViewById(R.id.btnCart);
        LinearLayout btnHome = findViewById(R.id.btnHome);
        LinearLayout btnProfile = findViewById(R.id.btnProfile);
        LinearLayout btnHelp = findViewById(R.id.btnHelp);
        LinearLayout btnUpdate = findViewById(R.id.btnSoon);
        ConstraintLayout btnOrder = findViewById(R.id.btnOrder);


        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SupportActivity.class));
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Coming Soon !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void recyclerViewCategory(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList=findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category = new ArrayList<>();
        category.add(new CategoryDomain("Laundry", "wet_laundry.png"));
        category.add(new CategoryDomain("Dry Clean", "dry_laundry.png"));
        category.add(new CategoryDomain("Premium", "premium_wash.png"));
        category.add(new CategoryDomain("Iron", "iron.png"));
        category.add(new CategoryDomain("Update", "update.png"));

        adapter= new CategoryAdaptor(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    private void recycleViewServices(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewServicesList=findViewById(R.id.recyclerView2);
        recyclerViewServicesList.setLayoutManager(linearLayoutManager);

        ArrayList<ServicesDomain> serviceList = new ArrayList<>();
        serviceList.add(new ServicesDomain("Shirt", "tshirt", "We will make your shirt as clean as new", 2.5));
        serviceList.add(new ServicesDomain("Jeans", "jeans", "We will make your Jeans as clean as new", 5.5));
        serviceList.add(new ServicesDomain("Jacket", "jacket", "We will make your Jacket as clean as new", 8.0));
        serviceList.add(new ServicesDomain("Blanket", "blanket", "We will make your Blanket as clean as new", 10.5));
        serviceList.add(new ServicesDomain("Carpet  ", "carpet", "We will make your shirt as clean as new", 25.5));

        adapter2 = new ServicesAdaptor(serviceList);
        recyclerViewServicesList.setAdapter(adapter2);
    }
}