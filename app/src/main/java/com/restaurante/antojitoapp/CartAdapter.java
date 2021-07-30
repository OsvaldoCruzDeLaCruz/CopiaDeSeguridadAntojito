package com.restaurante.antojitoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.restaurante.antojitoapp.Model.CartElement;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    private List<CartElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    final  CartAdapter.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(CartElement item);
    }

    public CartAdapter(List<CartElement> itemList, Context context, CartAdapter.OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;


    }
    
    @Override
    public int getItemCount(){
        return mData.size();
    }
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.cart_items_layout, null);
        return new CartAdapter.ViewHolder(view);
     }
     @Override
    public void onBindViewHolder(final CartAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));

     }

     public void setItems(List<CartElement> item){
        mData = item;
     }

     public class ViewHolder extends RecyclerView.ViewHolder{
        TextView productoNombre, precioTotal, productoCantidad;

        ViewHolder(View itemView){
            super(itemView);
            productoNombre = itemView.findViewById(R.id.nombre_producto_cart);
            productoCantidad = itemView.findViewById(R.id.cantidad_producto_cart);
            precioTotal = itemView.findViewById(R.id.precio_producto_cart);

        }
        void bindData(final CartElement item){
            productoNombre.setText(item.getNombreProducto());
            productoCantidad.setText(item.getCantidadProducto());
            precioTotal.setText(item.getTotal());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

        }
     }
}
