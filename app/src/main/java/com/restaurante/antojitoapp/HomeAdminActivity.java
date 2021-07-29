package com.restaurante.antojitoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
                scan();
//                Intent intent = new Intent(HomeAdminActivity.this, ScanActivity.class );
//                startActivity(intent);
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

    private void scan() {
        IntentIntegrator integrator = new IntentIntegrator(HomeAdminActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Apunta al codigo del cliente");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


}