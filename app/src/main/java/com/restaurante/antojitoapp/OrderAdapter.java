package com.restaurante.antojitoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.restaurante.antojitoapp.Model.OrderElement;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<OrderElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    final OrderAdapter.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClik(OrderElement item);

    }


    public OrderAdapter(List<OrderElement> itemList, Context context, OrderAdapter.OnItemClickListener listener){
        this.mInflater= LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
    }
    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.order_element, null);
        return new OrderAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final OrderAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<OrderElement> items ){mData = items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id, total, numero;

        ViewHolder(View itemView){
            super(itemView);
            id = itemView.findViewById(R.id.idOrderTxt);
            total = itemView.findViewById(R.id.totalOrderTxt);
            numero = itemView.findViewById(R.id.numberOrderTxt);

        }

        void bindData(final OrderElement item){
            id.setText(item.getIdOrder());
            total.setText(item.getTotal());
            numero.setText(item.getTelefono());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClik(item);
                }
            });
        }
    }
}
