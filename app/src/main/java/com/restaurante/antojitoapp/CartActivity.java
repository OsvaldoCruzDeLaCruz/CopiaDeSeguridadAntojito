package com.restaurante.antojitoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
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
    final DatabaseReference RootRef =  FirebaseDatabase.getInstance().getReference();
    String numeroUsuario = Prevalent.currentOnlineUsers.getPhone().toString();
    HashMap<String, Object> products = new HashMap<>();
    HashMap<String, Object> information = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
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

                        precio = Integer.parseInt(ds.child("price").getValue().toString());
                        cantidad = Integer.parseInt(ds.child("amountProduct").getValue().toString());

                        total = (precio * cantidad);
                        totalString = String.valueOf(total);
                        cantidadString = String.valueOf(cantidad);

//                        listaProductosCart.add(new CartElement(id, image, ("Producto: "+nombre), tipo, ("Cantidad: " + cantidadString), ("Total a pagar: "+totalString), categoria));
                        listaProductosCart.add(new CartElement(pid, image, nombre, descripcion, cantidadString, totalString, categoria));
                        products.put("pid",pid);
                        products.put("image", image);
                        products.put("pname", nombre);
                        products.put("description", descripcion);
                        products.put("category", categoria);

                        System.out.println(nombre );
                        System.out.println(precio );
                        System.out.println(cantidad );
                        System.out.println(listaProductosCart);

                        information.put("total", totalString);
                        information.put("status", "0");

                        information.put("phone", numeroUsuario);


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

        System.out.println("anio"+anioF);
        System.out.println(diaF);
        System.out.println(horaF);
        System.out.println(minutoF);
        System.out.println(segundoF);
        idOrder = numeroUsuario+anioF+diaF+horaF+minutoF+segundoF;
        information.put("oid", idOrder);





    //Genera la orden para la tienda
                RootRef.child("Orders").child("OrdersStore").child(idOrder).child(numeroUsuario).child("Products").setValue(listaProductosCart)
                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull @NotNull Task<Void> task) {

                              //agregando informacion al pedido
                              RootRef.child("Orders").child("OrdersStore").child(idOrder).child(numeroUsuario).updateChildren(information)
                                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                                          @Override
                                          public void onComplete(@NonNull @NotNull Task<Void> task) {
//                                              generateOrderToCostomer();

//     Genera el pedido para el cliente

                                              RootRef.child("Orders").child("OrdersCostumers").child(numeroUsuario).child(idOrder).child("Products").setValue(listaProductosCart)
                                                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                          @Override
                                                          public void onComplete(@NonNull @NotNull Task<Void> task) {

                                                              RootRef.child("Orders").child("OrdersCostumers").child(numeroUsuario).child(idOrder).updateChildren(information)
                                                                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                          @Override
                                                                          public void onComplete(@NonNull @NotNull Task<Void> task) {

//                                        Verifica que se ingresaron correctamente los valores

                                                                              RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                  @Override
                                                                                  public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                                                      if(snapshot.child("Orders").child("OrdersCostumers").child(numeroUsuario).child(idOrder).child("oid").exists()){

                                                                                          lodingBar.dismiss();
                                                                                          Toast.makeText(CartActivity.this, "Orden creada con exito", Toast.LENGTH_SHORT).show();
                                                                                          Intent intent = new Intent(CartActivity.this, QrActivity.class);
                                                                                          intent.putExtra("idOrder", idOrder);
                                                                                          startActivity(intent);

                                                                                      }
                                                                                  }

                                                                                  @Override
                                                                                  public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                                                                  }
                                                                              });

                                                                          }
                                                                      });
                                                          }
                                                      });


                                          }
                                      });

                          }
                      });
    }




    public void generateOrderToCostomer(){

        RootRef.child("Orders").child("OrdersCostumers").child(numeroUsuario).child(idOrder).child("Products").setValue(listaProductosCart)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {

                        RootRef.child("Orders").child("OrdersCostumers").child(numeroUsuario).child(idOrder).updateChildren(information)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {

//                                        Agrega lo



                                    }
                                });


                    }
                });


//        validateOrder();
    }
    public void validateOrder(){
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.child("Orders").child("OrdersCostumers").child(numeroUsuario).child(idOrder).child("oid").exists()){

                    lodingBar.dismiss();
                    Toast.makeText(CartActivity.this, "Orden creada con exito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CartActivity.this, QrActivity.class);
                    intent.putExtra("idOrder", idOrder);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}