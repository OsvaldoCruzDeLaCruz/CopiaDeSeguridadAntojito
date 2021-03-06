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


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.restaurante.antojitoapp.Model.ListElement;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference mData;
    Context context;
    RecyclerView recyclerView;
    ArrayList<ListElement> listaProductos;



    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_first, container, false);
        context = getContext();
        listaProductos = new ArrayList<>();
        recyclerView = vista.findViewById(R.id.recyclerId);
        recyclerView.setHasFixedSize(true); //Problema si se genera el apk
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton btnCart =  vista.findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });
        llenarLista();
        return vista;
    }

    private void llenarLista() {



        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String id = ds.child("pid").getValue().toString();
                        String imagen = ds.child("image").getValue().toString();
                        String nombre = ds.child("pname").getValue().toString();
                        String precio = ds.child("price").getValue().toString();
                        String descripcion = ds.child("description").getValue().toString();
                        String categoria = ds.child("category").getValue().toString();
                        String cantidad = ds.child("amount").getValue().toString();


                        listaProductos.add(new ListElement(id,imagen, nombre, descripcion, precio, categoria, cantidad));
//
//                        listaProductos.add(new ListElement("R.drawable.hamburguesa","Pizza","Pizza","Grande"));
//                        listaProductos.add(new ListElement("R.drawable.hamburguesa","HotDog","HotDog","Grande"));
//                        listaProductos.add(new ListElement("R.drawable.hamburguesa","Papas","Papas","Grande"));
                    }
                }
                ListAdapter adapter = new ListAdapter(listaProductos, context, new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ListElement item) {
                        moveToDescription(item);
                    }

                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    public void moveToDescription(ListElement item){
        Intent intent = new Intent(getContext(), DescriptionActivity.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);

    }
}