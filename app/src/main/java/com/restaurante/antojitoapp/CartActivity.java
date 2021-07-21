package com.restaurante.antojitoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.restaurante.antojitoapp.Model.Cart;
import com.restaurante.antojitoapp.Prevalent.Prevalent;

import org.jetbrains.annotations.NotNull;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button crearPedidobtn;
    private TextView txtTotalAPagar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        crearPedidobtn = (Button) findViewById(R.id.creaPedido_btn);
        txtTotalAPagar = (TextView) findViewById(R.id.totalAPagar);

    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("PedidosUsuarios")
                    .child(Prevalent.currentOnlineUsers.getPhone())
                    .child("Product"), Cart.class)
                    .build();
        FirebaseRecyclerAdapter<Cart, CartAdapter> adapter = new FirebaseRecyclerAdapter<Cart, CartAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull CartAdapter holder, int position, @NonNull @NotNull Cart model) {
                holder.txtProductoCantidad.setText(model.getAmountProduct());
                holder.txtProductoNombre.setText(model.getPname());
                holder.txtProductoPrecio.setText(model.getPrice());
            }

            @NonNull
            @NotNull
            @Override
            public CartAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartAdapter holder = new CartAdapter(view);
                return  holder;
            }
        };
    }
}