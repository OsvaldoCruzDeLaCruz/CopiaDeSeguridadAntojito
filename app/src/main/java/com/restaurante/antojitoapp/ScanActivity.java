package com.restaurante.antojitoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class ScanActivity extends AppCompatActivity {

    TextView oid, titleTotalCash;
    Button btnPay;
    Context context;
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        String element = (String) getIntent().getSerializableExtra("idOrder");
        orderId = element.toString();

        oid = findViewById(R.id.titleIdOrderAdmin);
        oid.setText(orderId);

        titleTotalCash = findViewById(R.id.titleTotalCash);
        btnPay = findViewById(R.id.btnPay);
        context = this;



        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payOrder();
            }
        });
    }

    private void payOrder() {


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.child("Orders").child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String phone = ds.child("phone").getValue().toString();
                        String statusOld = ds.child("status").getValue().toString();
                        String total = ds.child("total").getValue().toString();

                            if(ds.exists()){
                               String status = "1";

                                RootRef.child("Orders").child(orderId).child(phone).child("status").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Intent intent = new Intent(ScanActivity.this, HomeAdminActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(context, "Pagado con exito", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });



                            }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }


}