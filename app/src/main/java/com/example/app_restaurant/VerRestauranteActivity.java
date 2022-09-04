package com.example.app_restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.app_restaurant.models.Comentario;
import com.example.app_restaurant.models.Like;
import com.example.app_restaurant.models.Restaurante;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.UUID;

public class VerRestauranteActivity extends AppCompatActivity {

    private Restaurante restaurante;
    private FirebaseAuth mAuth;
    private ImageView imageView;
    private TextView txtNombreRestaurante, txtDescripcion, txtDetallesUbi,txtLike,txtDepartamento;
    private DatabaseReference mDatabase;
    private FloatingActionButton btnlike,quitar;
    DatabaseReference likerefence;



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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnlike = findViewById(R.id.btnLike);
        quitar = findViewById(R.id.btnQuitar);
        mostrarDatosRestauranteSeleccionado();

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        //isLikes(restaurante.getId_restaurante(), btnlike);
        nrLikes(txtLike, restaurante.getId_restaurante());
        validarInicioSesion();


        btnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mAuth.getCurrentUser() !=null){//usuario iniciSesion

                    FirebaseDatabase.getInstance().getReference().child("Likes").child(restaurante.getId_restaurante())
                            .child(firebaseUser.getUid()).setValue(true);

                }else{

                    Toast.makeText(VerRestauranteActivity.this, "Debe de Inicar sesion", Toast.LENGTH_SHORT).show();

                }
            }
        });
        quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAuth.getCurrentUser() !=null){//usuario iniciSesion

                    FirebaseDatabase.getInstance().getReference().child("Likes").child(restaurante.getId_restaurante())
                            .child(firebaseUser.getUid()).removeValue();

                }

            }
        });

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





    public void isLikes(String postid, ImageView image){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(restaurante.getId_restaurante());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(firebaseUser.getUid()).exists()){
                    btnlike.setImageResource(R.drawable.ic_liked);
                    btnlike.setTag("Liked");
                }else{
                    btnlike.setImageResource(R.drawable.ic_like);
                    btnlike.setTag("quitar");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    public void nrLikes(TextView txtLike, String restauranteid){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Likes").child(restauranteid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtLike.setText(snapshot.getChildrenCount()+" likes");
                mDatabase.child("Restaurantes").child(restaurante.getId_restaurante()).child("likes").setValue(snapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void validarInicioSesion(){

        if(mAuth.getCurrentUser() !=null){//usuario iniciSesion

            isLikes(restaurante.getId_restaurante(), btnlike);

        }else{

        }

    }



}