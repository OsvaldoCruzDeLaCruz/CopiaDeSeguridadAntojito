package com.restaurante.antojitoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.restaurante.antojitoapp.Model.CartElement;
import com.restaurante.antojitoapp.Model.ListElement;
import com.restaurante.antojitoapp.Prevalent.Prevalent;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    List<CartElement> listaProductosCart;
    RecyclerView recyclerView;
    private ProgressDialog lodingBar;
    Context context = this;
    Button btnGenerate;
    int precio;
    int total;
    int cantidad;
    String idOrder;
    String totalString;
    String pid;
    String cantidadString;
    int totalAPagar;
    String totalAPagarString;
    final DatabaseReference RootRef =  FirebaseDatabase.getInstance().getReference();
    String numeroUsuario;


    HashMap<String, Object> data = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        totalAPagar = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        numeroUsuario = Prevalent.currentOnlineUsers.getPhone().toString();
        btnGenerate = findViewById(R.id.btnGenerate);
        recyclerView = findViewById(R.id.CartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listaProductosCart = new ArrayList<>();
        lodingBar = new ProgressDialog(this);


        init();

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lodingBar.setTitle("Creando tu pedido");
                lodingBar.setMessage("Porfavor espera, ya casi terminamos");
                lodingBar.setCanceledOnTouchOutside(false);
                lodingBar.show();

                generateOrderToStore();
            }
        });



    }


    public void init(){


        RootRef.child("CartList").child("PedidosUsuarios").child(numeroUsuario).child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        pid = ds.child("pid").getValue().toString();
                        String image = ds.child("image").getValue().toString();
                        String nombre = ds.child("pname").getValue().toString();
                        String descripcion = ds.child("description").getValue().toString();
                        String categoria = ds.child("category").getValue().toString();
                        String precioUnitario = ds.child("price").getValue().toString();
                        precio = Integer.parseInt(precioUnitario);
                        cantidad = Integer.parseInt(ds.child("amountProduct").getValue().toString());

                        total = (precio * cantidad);
                        totalString = String.valueOf(total);
                        cantidadString = String.valueOf(cantidad);

                        listaProductosCart.add(new CartElement(pid, image, nombre, descripcion, cantidadString, precioUnitario ,totalString, categoria));


                        totalAPagar = totalAPagar + total;

                    }
                    totalAPagarString = String.valueOf(totalAPagar);

                    data.put("status", "0");
                    data.put("phone", numeroUsuario);
                    data.put("total", totalAPagarString);
                }else{
                    btnGenerate.setVisibility(View.INVISIBLE);
                }

                CartAdapter cartAdapter = new CartAdapter(listaProductosCart, context, new CartAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(CartElement item) {

                        CharSequence option[] = new CharSequence[]{
                            "Cambiar cantidad",
                            "Eliminar"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Opciones");
                        builder.setItems(option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String pid = item.getIdProducto();
                                String imagen = item.getImage();
                                String name = item.getNombreProducto();
                                String type = item.getDescripcion();
                                String size = item.getPrecio();
                                String category = item.getCategoria();
                                String amount= item.getCantidadProducto();

                                if(i == 0){
                                    ListElement listElement = new ListElement(pid, imagen, name, type, size, category, amount);
                                    moveToDescription(listElement);
                                }else if(i == 1){
                                    RootRef.child("CartList").child("PedidosUsuarios").child(numeroUsuario).child("Products").child(pid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(context,"Borrado de tu carrito", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                            }

                                        }
                                    });
                                }


                            }
                        });
                      builder.show();

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

    private void generateOrderToStore() {

//Obteniendo las fechas para poder hacer un id junto el numero celular del usuario
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat anio = new  SimpleDateFormat("yy");
        String anioF = anio.format(calForDate.getTime());
//        SimpleDateFormat mes = new  SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dia = new  SimpleDateFormat("DD");
        String diaF = dia.format(calForDate.getTime());
        SimpleDateFormat hora = new  SimpleDateFormat("kK");
        String horaF = hora.format(calForDate.getTime());
        SimpleDateFormat minuto = new  SimpleDateFormat("mm");
        String minutoF = minuto.format(calForDate.getTime());
        SimpleDateFormat segundo = new  SimpleDateFormat("sss");
        String segundoF = segundo.format(calForDate.getTime());
        SimpleDateFormat milesegundo = new  SimpleDateFormat("SS");
        String milesegundoF = segundo.format(calForDate.getTime());

        idOrder = numeroUsuario+anioF+diaF+horaF+minutoF+segundoF+milesegundoF;

        data.put("oid", idOrder);
        data.put("Product", listaProductosCart);


        RootRef.child("Orders").child(idOrder).child(numeroUsuario).setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {

                        if(task.isSuccessful()){
                            deleteCart();
                        }
                        else {
                            callback();
                        }
                    }
                });


    }

    private void deleteCart() {
        RootRef.child("CartList").child("PedidosUsuarios").child(numeroUsuario).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(CartActivity.this, "Orden creada con exito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CartActivity.this, QrActivity.class);
                    intent.putExtra("idOrder", idOrder);
                    startActivity(intent);
                    lodingBar.dismiss();
                }
            }
        });
    }

    public void callback(){
        generateOrderToStore();
    }

}