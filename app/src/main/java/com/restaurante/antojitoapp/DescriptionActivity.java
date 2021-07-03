package com.restaurante.antojitoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.restaurante.antojitoapp.Model.ListElement;
import com.squareup.picasso.Picasso;

public class DescriptionActivity extends AppCompatActivity {


    ImageView imagen;
    TextView nombre ;
    TextView descripcion;
    TextView precio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");
        imagen  = findViewById(R.id.imageViewProduct);
        nombre = findViewById(R.id.titleProduct);
        descripcion = findViewById(R.id.descriptionProduct);
        precio = findViewById(R.id.priceProduct);


        Picasso.with(this).load(element.getImagen())
                .error(R.drawable.hamburguesa)
                .fit()
                .centerInside()
                .into(imagen);
        nombre.setText(element.getName());
        descripcion.setText(element.getType());
        precio.setText(element.getSize());

    }
}