package com.example.app_restaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_restaurant.R;
import com.example.app_restaurant.models.Comentario;
import com.example.app_restaurant.models.Restaurante;


import java.util.List;

public class ListaComentariosAdapter extends RecyclerView.Adapter<ListaComentariosAdapter.ViewHolder>{
    private List<Comentario> data;
    private LayoutInflater inflater;
    private Context context;

public ListaComentariosAdapter(List<Comentario> item, Context context){
    this.inflater = LayoutInflater.from(context);
    this.context = context;
    this.data = item;

}

    @NonNull
    @Override
    public ListaComentariosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_comentarios, parent,false);
        return new ListaComentariosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaComentariosAdapter.ViewHolder holder, int position) {

        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setItem(List<Comentario> items) { data = items; }

    public class ViewHolder extends RecyclerView.ViewHolder {

    TextView nombre,fecha,comentario;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tvNombre);
            fecha = itemView.findViewById(R.id.tvFecha);
            comentario = itemView.findViewById(R.id.tvComentario);

        }

        void bindData(final Comentario item){

            nombre.setText(item.getUsuario());
            fecha.setText(item.getFecha());
            comentario.setText(item.getComentario());

        }

    }
}
