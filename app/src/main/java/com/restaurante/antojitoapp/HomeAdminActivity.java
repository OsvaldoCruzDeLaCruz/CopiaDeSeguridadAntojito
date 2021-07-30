package com.restaurante.antojitoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;

public class HomeAdminActivity extends AppCompatActivity {

    Button startScannig, starListOrders, exit;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        context = this;
        startScannig =  findViewById(R.id.btnGoScan);
        starListOrders = findViewById(R.id.btnGoOrders);
        exit = findViewById(R.id.exitbtnAdmin);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeAdminActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        startScannig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan();
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
            }else {
                    String numscan = result.getContents();
                    final DatabaseReference RootRef;
                    RootRef = FirebaseDatabase.getInstance().getReference();

                    RootRef.child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    if(ds.exists()){
                                    for (DataSnapshot ds2 : ds.getChildren()) {
                                        if (ds2.exists()) {
                                            String oid = ds2.child("oid").getValue().toString();
                                            String status = ds2.child("status").getValue().toString();
                                            if (oid.equals(numscan)) {
                                                if (status.equals("0")) {
                                                    Intent intent = new Intent(HomeAdminActivity.this, ScanActivity.class);
                                                    intent.putExtra("idOrder", result.getContents());
                                                    startActivity(intent);
                                                    Toast.makeText(context, "Escaneo correcto", Toast.LENGTH_SHORT).show();
                                                } else if (status.equals("1")) {
                                                    Toast.makeText(context, "Ya esta pagado el pedido", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        }
                                    }
                                }
                                    else {
                                        Toast.makeText(context, "No existe ningun pedido con ese codigo", Toast.LENGTH_SHORT).show();
                                    }
                           }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });


            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


}