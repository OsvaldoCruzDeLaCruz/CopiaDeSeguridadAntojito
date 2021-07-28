package com.restaurante.antojitoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeAdminActivity extends AppCompatActivity {

    Button startScannig, starListOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        startScannig =  findViewById(R.id.btnGoScan);
        starListOrders = findViewById(R.id.btnGoOrders);

        startScannig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAdminActivity.this, ScanActivity.class );
                startActivity(intent);
            }
        });

        starListOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAdminActivity.this, OrdersAdminActivity.class);
                startActivity(intent);
            }
        });
    }



}