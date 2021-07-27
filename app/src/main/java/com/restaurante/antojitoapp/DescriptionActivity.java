package com.restaurante.antojitoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.restaurante.antojitoapp.Model.ListElement;
import com.restaurante.antojitoapp.Prevalent.Prevalent;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DescriptionActivity extends AppCompatActivity {


    ImageView imagen;
    TextView nombre ;
    TextView descripcion;
    TextView precio;
    EditText cantidad;
    Button boton;

    String idProducto, imagenProducto, nombreProducto, descripcionProducto, precioProducto, cantidadProducto, image, category;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");

        idProducto = element.getId().toString();
        imagenProducto = element.getImagen().toString();
        nombreProducto = element.getName().toString();
        descripcionProducto = element.getType().toString();
        precioProducto =  element.getSize().toString();
        image = element.getImagen().toString();
        category = element.getCategory().toString();


        imagen  = findViewById(R.id.imageViewProduct);
        nombre = findViewById(R.id.titleProduct);
        descripcion = findViewById(R.id.descriptionProduct);
        precio = findViewById(R.id.priceProduct);
        cantidad = findViewById(R.id.amountProduct);
        boton = findViewById(R.id.addToCart);

        Picasso.with(this).load(imagenProducto)
                .error(R.drawable.hamburguesa)
                .fit()
                .centerInside()
                .into(imagen);
        nombre.setText(nombreProducto);
        descripcion.setText(descripcionProducto);
        precio.setText(precioProducto);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCartList();
            }
        });

    }

    private void addToCartList() {
        cantidadProducto = cantidad.getText().toString();

        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat  currentDate = new  SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat  currentTime = new  SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("pid", idProducto);
        cartMap.put("pname", nombreProducto);
        cartMap.put("price", precioProducto);
        cartMap.put("image", image);
        cartMap.put("category", category);
        cartMap.put("description", descripcionProducto);
        cartMap.put("amountProduct", Integer.parseInt(cantidadProducto));
        cartMap.put("time", saveCurrentTime);
        cartMap.put("date", saveCurrentDate);


        cartListRef.child("PedidosUsuarios").child(Prevalent.currentOnlineUsers.getPhone())
                .child("Products").child(idProducto)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    cartListRef.child("PedidosTienda").child(Prevalent.currentOnlineUsers.getPhone())
                            .child("Products").child(idProducto)
                            .updateChildren(cartMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(DescriptionActivity.this, "Agregado a tu carrito", Toast.LENGTH_SHORT).show();



                                    }
                                }
                            });
                }

                
            }
        });

        Intent intent = new Intent(DescriptionActivity.this, HomeActivity.class);
        startActivity(intent);
        
    }
}