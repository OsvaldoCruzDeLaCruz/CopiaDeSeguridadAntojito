package com.restaurante.antojitoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.restaurante.antojitoapp.Model.ListElement;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ScanActivity extends AppCompatActivity {

    TextView oid, titleTotalCash;
    Button btnPay;
    Context context;
    String orderId;
    String total;
    DatabaseReference RootRef;
    RecyclerView recyclerView;
    ArrayList<ListElement> listaProductos;
    String element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        listaProductos = new ArrayList<>();

        context = this;
        recyclerView = findViewById(R.id.ProductRecyclerViewOrderToPay);
        recyclerView.setHasFixedSize(true); //Problema si se genera el apk
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        element = (String) getIntent().getSerializableExtra("idOrder");
        orderId = element.toString();

;

        oid = findViewById(R.id.titleIdOrderAdmin);
        titleTotalCash = findViewById(R.id.titleTotalCash);
        btnPay = findViewById(R.id.btnPay);


        RootRef = FirebaseDatabase.getInstance().getReference();

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payOrder();
            }
        });

        llenarLista();
    }





    private void payOrder() {

        RootRef.child("Orders").child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String phone = ds.child("phone").getValue().toString();
                        String statusOld = ds.child("status").getValue().toString();
                        String total = ds.child("total").getValue().toString();
                        String status = ds.child("status").getValue().toString();

                        if(status.equals("0")){
                            if (ds.exists()) {


                                RootRef.child("Orders").child(orderId).child(phone).child("status").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(ScanActivity.this, HomeAdminActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(context, "Pagado con exito", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            }
                        }else{
                            Toast.makeText(context, "Ya esta pagado", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    public void llenarLista(){
        oid.setText("ID: "+orderId);
        RootRef.child("Orders").child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        String numberCostumer = ds.child("phone").getValue().toString();
                        total = ds.child("total").getValue().toString();

                        RootRef.child("Orders").child(orderId).child(numberCostumer).child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    for(DataSnapshot ds : snapshot.getChildren()){
                                        String id = ds.child("idProducto").getValue().toString();
                                        String image = ds.child("image").getValue().toString();
                                        String name = ds.child("nombreProducto").getValue().toString();
                                        String description = ds.child("descripcion").getValue().toString();
                                        String price = ds.child("precio").getValue().toString();
                                        String category = ds.child("categoria").getValue().toString();
                                        listaProductos.add(new ListElement(id,image, name, description, price, category));
                                    }

                                }
                                ListAdapter adapter = new ListAdapter(listaProductos, context, new ListAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(ListElement item) {
                                        noMoreAction();
                                    }
                                });
                                recyclerView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });


                    }
                    titleTotalCash.setText("Total a pagar: $"+total);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }

    private void noMoreAction() {


    }


}