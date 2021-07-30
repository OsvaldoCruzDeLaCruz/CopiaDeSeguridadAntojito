package com.restaurante.antojitoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.restaurante.antojitoapp.Model.OrderElement;
import com.restaurante.antojitoapp.Prevalent.Prevalent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdminActivity extends AppCompatActivity {
    List<OrderElement> listaOrdenes;
    private DatabaseReference mData;
    Context context;
    RecyclerView recyclerView;
    String numeroUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_admin);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerOrdersAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        context = this;
        listaOrdenes = new ArrayList<>();
        numeroUsuario = Prevalent.currentOnlineUsers.getPhone();
        llenarLista();

    }

    private void llenarLista() {

        mData = FirebaseDatabase.getInstance().getReference();


        mData.child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        for (DataSnapshot ds2 : ds.getChildren()) {
                            if(ds2.exists()) {
                                String oid = ds2.child("oid").getValue().toString();
                                String phone = ds2.child("phone").getValue().toString();
                                String total = ds2.child("total").getValue().toString();
                                System.out.println("``````````` " + oid);

                                listaOrdenes.add(new OrderElement(oid, total, phone));

                                OrderAdapter orderAdapter = new OrderAdapter(listaOrdenes, context, new OrderAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClik(OrderElement item) {
                                        moveToDescription(item);
                                    }
                                });
                                recyclerView.setAdapter(orderAdapter);
                            }

                         }
                    }



                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void moveToDescription(OrderElement item) {
        Intent intent = new Intent(context, ScanActivity.class);
        intent.putExtra("idOrder", item.getIdOrder());
        startActivity(intent);


    }
}