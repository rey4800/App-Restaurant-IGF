package com.example.app_restaurant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgregarRestauranteActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Spinner spinner1;
    private EditText txtNombreRestaurante, txtDescripcion, txtGPS, txtDetallesUbi;
    private ImageView imageView;

    //Variable para manejar imagen
    private static final int File = 1;
    public Uri FileUri;
    public String urlcopia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_restaurante);

        //Instanciar los obetos de firebase

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Inicilization variables
        spinner1 =  findViewById(R.id.spinner);
        txtNombreRestaurante = findViewById(R.id.txtNombreRestaurante);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtGPS = findViewById(R.id.txtGPS);
        txtDetallesUbi = findViewById(R.id.txtDetallesUbi);
        imageView = findViewById(R.id.imageView);


        //Llenar sspinner con los departamentos
        String [] opciones = {"Departamento...","Usulután","Sonsonate","Santa Ana","San Vicente","San Vicente"
                ,"San Miguel","Morazán","La Unión","La Paz","La Libertad","Cuscatlán","Chalatenango","Cabañas","Ahuachapán"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        spinner1.setAdapter(adapter);

        //String seleccion = spinner1.getSelectedItem().toString();

    }

    public void btnAgregarRestaurante(View view){



    }

    //Cargar Imagen de perfil

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

        if(requestCode == File){
            if(resultCode == RESULT_OK){
                FileUri = data.getData();
                Glide.with(AgregarRestauranteActivity.this)
                        .load(FileUri)
                        .apply(new RequestOptions().override(700,400))
                        .into(imageView);
            }else{
                Toast.makeText(this, "Error al cargar esta imagen", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "No se pudo cargar ningun archivo", Toast.LENGTH_SHORT).show();
        }


    }



}