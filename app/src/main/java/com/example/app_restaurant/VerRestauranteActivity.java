package com.example.app_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app_restaurant.models.Restaurante;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.List;

public class VerRestauranteActivity extends AppCompatActivity {

    private Restaurante restaurante;
    private FirebaseAuth mAuth;
    private ImageView imageView;
    private TextView txtNombreRestaurante, txtDescripcion, txtDetallesUbi,txtLike,txtDepartamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_restaurante);

        restaurante = new Restaurante();
        restaurante = (Restaurante) getIntent().getExtras().getSerializable("restaurante");
        Log.d("restaurante", String.valueOf(restaurante.coordenadas.latitud));

        //Inicilization Variables

        txtNombreRestaurante = findViewById(R.id.txtNombreRestaurante);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtDetallesUbi = findViewById(R.id.txtDetallesUbi);
        txtDepartamento = findViewById(R.id.txtDepartamento);
        txtLike = findViewById(R.id.txtLike);
        imageView = findViewById(R.id.image);
        mAuth = FirebaseAuth.getInstance();

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

    public void btnComoLlegar(View view){
        Uri uri = Uri.parse("geo:" + restaurante.coordenadas.latitud + "," + restaurante.coordenadas.longitud + "?z=16&q=" + restaurante.coordenadas.latitud + "," + restaurante.coordenadas.longitud + "(" + restaurante.getNombre() + ")");
        startActivity( new Intent(Intent.ACTION_VIEW, uri));
    }


    public void designBotonLike(){

        if(mAuth.getCurrentUser() !=null){//usuario iniciSesion

            startActivity(new Intent(VerRestauranteActivity.this,AgregarRestauranteActivity.class));
            // finish();

        }else{



        }



    }

}