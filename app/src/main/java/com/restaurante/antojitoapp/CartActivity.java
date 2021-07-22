package com.restaurante.antojitoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.restaurante.antojitoapp.Model.ListElement;
import com.restaurante.antojitoapp.Prevalent.Prevalent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    String nombreProducto;
    String cantidadProducto;
    String precioUnitarioProducto;
    String numeroUsuario;
    String totalString;
    ArrayList<CartElement> listaProductosCart;
    int total, precioInt, cantidadInt;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }
    public void init(){
        numeroUsuario = Prevalent.currentOnlineUsers.getPhone().toString();
        mData.child("CartList").child("PedidosUsuarios").child(numeroUsuario).child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        String nombre = ds.child("pname").getValue().toString();
                        String precio = ds.child("price").getValue().toString();
                        String cantidad = ds.child("amountProduct").getValue().toString();


                        precioInt = Integer.parseInt(precio);
                        cantidadInt = Integer.parseInt(cantidad);
                        total = (precioInt * cantidadInt);
                        totalString = String.valueOf(total);

                        listaProductosCart.add(new CartElement(nombre, cantidadProducto, totalString));
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        CartAdapter cartAdapter = new CartAdapter(listaProductosCart, this);
        RecyclerView recyclerView = findViewById(R.id.CartRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);


    }
}