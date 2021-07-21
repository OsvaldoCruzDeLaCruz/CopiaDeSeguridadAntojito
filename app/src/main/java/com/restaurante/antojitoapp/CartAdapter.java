package com.restaurante.antojitoapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class CartAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtProductoNombre, txtProductoPrecio, txtProductoCantidad;

    public CartAdapter(View itemView) {
        super(itemView);
        txtProductoNombre = itemView.findViewById(R.id.nombre_producto_cart);
        txtProductoPrecio = itemView.findViewById(R.id.precio_producto_cart);
        txtProductoCantidad = itemView.findViewById(R.id.cantidad_producto_cart);
    }

    @Override
    public void onClick(View view) {

    }


    
}
