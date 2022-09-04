package com.example.app_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app_restaurant.models.Restaurante;

import java.io.Serializable;
import java.util.List;

public class VerRestauranteActivity extends AppCompatActivity {

    private Restaurante restaurante;

    private ImageView imageView;
    private TextView txtNombreRestaurante, txtDescripcion, txtDetallesUbi,txtLike,txtDepartamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_restaurante);

        restaurante = new Restaurante();
        restaurante = (Restaurante) getIntent().getExtras().getSerializable("restaurante");

        //Inicilization Variables

        txtNombreRestaurante = findViewById(R.id.txtNombreRestaurante);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtDetallesUbi = findViewById(R.id.txtDetallesUbi);
        txtDepartamento = findViewById(R.id.txtDepartamento);
        txtLike = findViewById(R.id.txtLike);
        imageView = findViewById(R.id.image);


        mostrarDatosRestauranteSeleccionado();



    }


    public void mostrarDatosRestauranteSeleccionado(){

        Glide.with(this).load(restaurante.getImagen()).into(imageView);
        txtNombreRestaurante.setText(restaurante.getNombre());
        txtDescripcion.setText(restaurante.getDescripcion());
        txtDetallesUbi.setText(restaurante.getUbicacion());
        txtDepartamento.setText(restaurante.getDepartamento());
        txtLike.setText(String.valueOf(restaurante.getLikes()));


    }

    public void btnVerComentarios(View view){

        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("restaurante", (Serializable) restaurante);
        startActivity(intent);



    }



}