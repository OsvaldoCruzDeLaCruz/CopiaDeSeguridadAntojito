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
import com.restaurante.antojitoapp.Model.CartElement;
import com.restaurante.antojitoapp.Model.ListElement;
import com.restaurante.antojitoapp.Prevalent.Prevalent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    List<CartElement> listaProductosCart;
    RecyclerView recyclerView;
    Context context = this;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.CartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listaProductosCart = new ArrayList<>();

        init();
    }
    public void init(){

        String numeroUsuario = Prevalent.currentOnlineUsers.getPhone().toString();
        mData.child("CartList").child("PedidosUsuarios").child(numeroUsuario).child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        String id = ds.child("pid").getValue().toString();
                        String image = ds.child("image").getValue().toString();
                        String nombre = ds.child("pname").getValue().toString();
                        String descripcion = ds.child("description").getValue().toString();
                        String categoria = ds.child("category").getValue().toString();

                        int precio = Integer.parseInt(ds.child("price").getValue().toString());
                        int cantidad = Integer.parseInt(ds.child("amountProduct").getValue().toString());

                        int total = (precio * cantidad);
                        String totalString = String.valueOf(total);
                        String cantidadString = String.valueOf(cantidad);

//                        listaProductosCart.add(new CartElement(id, image, ("Producto: "+nombre), tipo, ("Cantidad: " + cantidadString), ("Total a pagar: "+totalString), categoria));
                        listaProductosCart.add(new CartElement(id, image, nombre, descripcion, cantidadString, totalString, categoria));

                        System.out.println(nombre );
                        System.out.println(precio );
                        System.out.println(cantidad );
                        System.out.println(listaProductosCart);
                    }
                }

                CartAdapter cartAdapter = new CartAdapter(listaProductosCart, context, new CartAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(CartElement item) {
                        String id = item.getIdProducto();
                        String imagen = item.getImage();
                        String name = item.getNombreProducto();
                        String type = item.getDescripcion();
                        String size = item.getPrecio();
                        String category = item.getCategoria();

                        ListElement listElement = new ListElement(id, imagen, name, type, size, category);
                        moveToDescription(listElement);
                    }
                });
                recyclerView.setAdapter(cartAdapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }

    private void moveToDescription(ListElement item) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
    }
}