package com.example.app_restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_restaurant.adapter.ListaComentariosAdapter;
import com.example.app_restaurant.adapter.ListaRestaurantesAdapter;
import com.example.app_restaurant.models.Comentario;
import com.example.app_restaurant.models.Restaurante;
import com.example.app_restaurant.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CommentActivity extends AppCompatActivity {

    private Restaurante restaurante;
    private TextView like_box,txtCantidadC;
    private EditText txtComentario;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Comentario comentario;
    private User usuario;
    List<Comentario> listaComentarios;
    private ListaComentariosAdapter listaComentarioAdapter;
    RecyclerView recyclerView;

    //Declaracion de objetos para manipular las base de datos de firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //Instanciar los objetos de firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();//Obtenemos la refrencia de nuestra base de datos

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        comentario =  new Comentario();
        restaurante = new Restaurante();
        usuario = new User();
        restaurante = (Restaurante) getIntent().getExtras().getSerializable("restaurante");

        //Inicilization variables
        txtComentario = findViewById(R.id.txtComentario);
        recyclerView = findViewById(R.id.listComentarios);
        like_box = findViewById(R.id.like_box);
        txtCantidadC =findViewById(R.id.txtCantidadC);
        cargarComentarios();
        ObtenerCantidadComentarios();
        validarInicioSesion();

    }


    public void btnAgregarComentario(View view){

        String tvComentario = txtComentario.getText().toString();


        if(mAuth.getCurrentUser() !=null) {

            if (!tvComentario.isEmpty()) {
                if(validarCantidadComentarios()<3) {// comentarios < 3 por usuario

                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    String idUsu = mAuth.getCurrentUser().getUid();
                    comentario.setId(UUID.randomUUID().toString());
                    comentario.setId_Usu(idUsu);
                    comentario.setUsuario(usuario.getNombre());
                    comentario.setId_restaurante(restaurante.getId_restaurante());
                    comentario.setComentario(tvComentario);
                    comentario.setFecha(date);
                    comentario.setFiltro(restaurante.getId_restaurante() + "," + idUsu);
                    mDatabase.child("Comentarios").child(comentario.getId()).setValue(comentario);

                    Toast.makeText(this, "Realizo un comentario con Exito!", Toast.LENGTH_SHORT).show();
                    sumarPuntosUsuario();
                    ObtenerCantidadComentarios();
                    Toast.makeText(this, "Ha ganado 10 pt por comentar!", Toast.LENGTH_SHORT).show();
                    LimpiarCajasTexto();
                }else{

                    Toast.makeText(this, "Ya ha comentado tres veces este restaurante", Toast.LENGTH_LONG).show();
                }

            } else {

                Toast.makeText(this, "Debe escribir un comentario", Toast.LENGTH_SHORT).show();
            }

        }else{

            Toast.makeText(this, "Debe de Iniciar Sesion para Comentar", Toast.LENGTH_LONG).show();

        }
    }


    public void cargarComentarios(){
        listaComentarios = new ArrayList<>();
        listaComentarioAdapter = new ListaComentariosAdapter(listaComentarios,this);
        DatabaseReference comment = databaseReference.child("Comentarios");
        Query buscar = comment.orderByChild("id_restaurante").equalTo(restaurante.getId_restaurante());

        buscar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaComentarios.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Comentario comment = dataSnapshot.getValue(Comentario.class);
                    listaComentarios.add(comment);
                }
                listaComentarioAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listaComentarioAdapter);

    }


    public void ObtenerCantidadComentarios(){

        DatabaseReference comment = databaseReference.child("Comentarios");
        Query query = comment.orderByChild("id_restaurante").equalTo(restaurante.getId_restaurante());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int counter = (int) dataSnapshot.getChildrenCount();

                //Convert counter to string
                String cantidadComentarios = String.valueOf(counter);
                like_box.setText("Comentarios(" + cantidadComentarios + ")");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public int validarCantidadComentarios(){

        String idUsu = mAuth.getCurrentUser().getUid();
        DatabaseReference comment = databaseReference.child("Comentarios");
        Query query = comment.orderByChild("filtro").equalTo(restaurante.getId_restaurante() + "," + idUsu);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int counter = (int) dataSnapshot.getChildrenCount();
                String cantidadComentarios = String.valueOf(counter);
                txtCantidadC.setText(cantidadComentarios);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return Integer.parseInt(txtCantidadC.getText().toString());
    }


    private void getUserInfo() {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    usuario.setNombre(snapshot.child("nombre").getValue().toString());
                    usuario.setEmail(snapshot.child("email").getValue().toString());
                    usuario.setPassword(snapshot.child("password").getValue().toString());
                    usuario.setPuntaje(Integer.parseInt(snapshot.child("puntaje").getValue().toString()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.e("Error database", error.toString());

            }
        });
    }

    public void sumarPuntosUsuario(){

        String id = mAuth.getCurrentUser().getUid();
        int puntaje = usuario.getPuntaje() + 10;
        usuario.setPuntaje(puntaje);


        databaseReference.child("Users").child(id).setValue(usuario);


    }

    public void btnVolverPantallaAnterior(View view){
        finish();
    }

    public void validarInicioSesion(){
        if(mAuth.getCurrentUser() !=null){//usuario iniciSesion

            getUserInfo(); //Obtener los datos del usuario logueado
            validarCantidadComentarios();
        }

    }

    public void LimpiarCajasTexto(){
        txtComentario.setText("");
    }



}