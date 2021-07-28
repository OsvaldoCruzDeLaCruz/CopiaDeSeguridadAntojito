package com.restaurante.antojitoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.restaurante.antojitoapp.Model.OrderElement;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdminActivity extends AppCompatActivity {
    List<OrderElement> listaOrdenes;
    private DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_admin);
        listaOrdenes = new ArrayList<>();
//        init();
    }
//    public void init(){
//
//        mData = FirebaseDatabase.getInstance().getReference();
//        mData.child("Orders").child("OrdersCostumers").child(numeroUsuario).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    for (DataSnapshot ds : snapshot.getChildren()) {
//
//                        String id = ds.child("oid").getValue().toString();
//                        String numero = ds.child("phone").getValue().toString();;
//                        String total = ds.child("total").getValue().toString();
//
//                        System.out.println("**********" + id);
//                        System.out.println("**********" + numero);
//                        System.out.println("**********" + total);
//
//                        listaOrdenes.add(new OrderElement(id, total, numero));
//                    }
//                }
//                OrderAdapter orderAdapter = new OrderAdapter(listaOrdenes, getContext());
//                recyclerView.setAdapter(orderAdapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//    }
}