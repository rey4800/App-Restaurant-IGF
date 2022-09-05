package com.example.app_restaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.app_restaurant.models.Coordenada;
import com.example.app_restaurant.models.Restaurante;
import com.example.app_restaurant.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class AgregarRestauranteActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Spinner spinner1;
    private EditText txtNombreRestaurante, txtDescripcion, txtGPS, txtDetallesUbi;
    private ImageView imageView;
    private  static final int AgregarUbicacion = 0;
    private Restaurante restaurante;
    private double latitud,longitud;
    private User usuario;

    //Variable para manejar imagen
    private static final int File = 1;
    public Uri FileUri;
    public String urlcopia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_restaurante);

        //Instanciar los objetos de firebase

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        restaurante =  new Restaurante();
        usuario = new User();

        //Inicilization variables
        spinner1 =  findViewById(R.id.spinner);
        txtNombreRestaurante = findViewById(R.id.txtNombreRestaurante);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtGPS = findViewById(R.id.txtGPS);
        txtDetallesUbi = findViewById(R.id.txtDetallesUbi);
        imageView = findViewById(R.id.imageView);


        //Llenar sspinner con los departamentos
        String [] opciones = {"Departamento...","Usulután","Sonsonate","Santa Ana","San Vicente","San Salvador"
                ,"San Miguel","Morazán","La Unión","La Paz","La Libertad","Cuscatlán","Chalatenango","Cabañas","Ahuachapán"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        spinner1.setAdapter(adapter);

        getUserInfo();//Obtener los datos del usuario


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

    public void btnAgregarRestaurante(View view){

        String idUsu = mAuth.getCurrentUser().getUid();
        String nombreRestaurante = txtNombreRestaurante.getText().toString();
        String descripcion = txtDescripcion.getText().toString();
        String detallesUbicacion = txtDetallesUbi.getText().toString();
        String seleccion = spinner1.getSelectedItem().toString();


        if(!nombreRestaurante.isEmpty() && !descripcion.isEmpty() && !detallesUbicacion.isEmpty()){
            if(latitud != 0.0 && longitud != 0.0){
                if(FileUri != null) {
                    StorageReference Folder = FirebaseStorage.getInstance().getReference().child("Restaurantes");
                    final StorageReference file_name = Folder.child("file" + FileUri.getLastPathSegment());
                    file_name.putFile(FileUri).addOnSuccessListener(taskSnapshot -> file_name.getDownloadUrl().addOnSuccessListener(uri -> {

                        restaurante.setId_Usu(idUsu);
                        restaurante.setId_restaurante(UUID.randomUUID().toString());
                        restaurante.setDepartamento(seleccion);
                        restaurante.setLikes(0);
                        restaurante.setDescripcion(descripcion);
                        restaurante.setUbicacion(detallesUbicacion);
                        restaurante.setNombre(nombreRestaurante);
                        restaurante.coordenadas = new Coordenada(latitud, longitud);

                        restaurante.setImagen(String.valueOf(uri));

                        mDatabase.child("Restaurantes").child(restaurante.getId_restaurante()).setValue(restaurante);

                        Toast.makeText(this, "Se guardo con exito", Toast.LENGTH_LONG).show();
                        finish();


                    }));

                }else{

                    Toast.makeText(this, "Debe Agregar una Imagen", Toast.LENGTH_SHORT).show();
                }
            }else{

                Toast.makeText(this, "Debe Agregar una Ubicacion", Toast.LENGTH_SHORT).show();
            }
    } else{
            Toast.makeText(this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
        }

    }



    public void AgregarUbicacionGPS(View view){

        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, AgregarUbicacion);


    }



    //Cargar Imagen

    public void botonCargar(View view){
        fileUpload();
    }

    public void fileUpload(){
        Intent intent =  new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,File);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == AgregarUbicacion  && resultCode == RESULT_OK){

            txtGPS.setText("Se agregó una ubicacion");
            latitud = data.getDoubleExtra("latitud",latitud);
            longitud = data.getDoubleExtra("longitud",longitud);
          //  txtGPS.setText(String.valueOf(latitud) + "," + String.valueOf(longitud) );

        }else{

            if (requestCode == File) {
                if (resultCode == RESULT_OK) {
                    FileUri = data.getData();
                    Glide.with(AgregarRestauranteActivity.this)
                            .load(FileUri)
                            .apply(new RequestOptions().override(700, 400))
                            .into(imageView);
                } else {
                    Toast.makeText(this, "Error al cargar esta imagen", Toast.LENGTH_SHORT).show();
                    FileUri = null;
                }

            } else {
                FileUri = null;
                Toast.makeText(this, "No se pudo cargar ningun archivo", Toast.LENGTH_SHORT).show();
            }

        }

    }

}