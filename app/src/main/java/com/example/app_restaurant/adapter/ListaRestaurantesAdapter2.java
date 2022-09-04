package com.example.app_restaurant.adapter;

import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_restaurant.R;
import com.example.app_restaurant.VerRestauranteActivity;
import com.example.app_restaurant.models.Restaurante;

import java.io.Serializable;
import java.util.List;

public class ListaRestaurantesAdapter2 extends RecyclerView.Adapter<ListaRestaurantesAdapter2.ViewHolder>{
    private List<Restaurante> data;
    private LayoutInflater inflater;
    private Context context;


    public ListaRestaurantesAdapter2(List<Restaurante> item, Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = item;
    }

    @Override
    public ListaRestaurantesAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_restaurante2, parent,false);
        return new ListaRestaurantesAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListaRestaurantesAdapter2.ViewHolder holder, final int position) {
        holder.bindData(data.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.itemView.getContext(), VerRestauranteActivity.class);
                intent.putExtra("restaurante", (Serializable) data.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return data.size();
    }



    public void setItem(List<Restaurante> items) { data = items; }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView nombre, descripcion, departamento;

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgList);
            nombre = itemView.findViewById(R.id.tvNombreItemRestaurante);
            descripcion = itemView.findViewById(R.id.tvDescripcionItemRestaurante);
            departamento = itemView.findViewById(R.id.tvDepartamentoItemRestaurante);

        }

        void bindData(final Restaurante item){
            //img.setColorFilter(Color.parseColor(item.getImagen()), PorterDuff.Mode.SRC_IN);
            Glide.with(context).load(item.getImagen()).into(img);
            nombre.setText("Nombre: " + item.getNombre());
            descripcion.setText("Descripción: " + item.getDescripcion());
            departamento.setText("Departamento: " + item.getDepartamento());

        }
    }
}
