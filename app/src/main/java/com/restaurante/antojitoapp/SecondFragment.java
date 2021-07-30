package com.restaurante.antojitoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.restaurante.antojitoapp.Model.OrderElement;
import com.restaurante.antojitoapp.Prevalent.Prevalent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference mData;
    RecyclerView recyclerView;
    ArrayList<OrderElement> listaOrdenes;
    Context context;
    String numeroUsuario ;


    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        numeroUsuario = Prevalent.currentOnlineUsers.getPhone();
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_second, container, false);
        listaOrdenes = new ArrayList<>();
        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerOrders);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        context = getContext();
        llenarLista();


        return vista;
    }

    private void llenarLista() {
        mData = FirebaseDatabase.getInstance().getReference();


        mData.child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        if(ds.child(numeroUsuario).child("oid").exists()){
                        String childre = ds.child(numeroUsuario).child("oid").getValue().toString();


                        mData.child("Orders").child(childre).child(numeroUsuario).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    System.out.println("*/*/*/*//*" + snapshot);

                                    String numero = snapshot.child("phone").getValue().toString();
                                    if (numero.equals(numeroUsuario)) {

                                        String id = snapshot.child("oid").getValue().toString();
                                        String total = snapshot.child("total").getValue().toString();

                                        System.out.println("**********" + id);
                                        System.out.println("**********" + numero);
                                        System.out.println("**********" + total);

                                        listaOrdenes.add(new OrderElement(id, total, numero));
                                    }
                                }

                                OrderAdapter orderAdapter = new OrderAdapter(listaOrdenes, context, new OrderAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClik(OrderElement item) {
                                        moveToQRActivity(item);
                                    }
                                });
                                recyclerView.setAdapter(orderAdapter);

                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                        //                          String phoneDS = ds.child("phone").getValue().toString();

                        }
                    }
                }



            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public void moveToQRActivity (OrderElement item){
        Intent intent = new Intent(context, QrActivity.class );
        intent.putExtra("idOrder", item.getIdOrder());
        startActivity(intent);
    }
}