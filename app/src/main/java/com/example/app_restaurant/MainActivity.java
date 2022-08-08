package com.example.app_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    /*Metodo o evento clic en el boton de iniciar sesion en la app*/
    public void clicIniciarSesion(View view){

        startActivity(new Intent(MainActivity.this,Login.class));
        //finish();

    }

    /*Metodo o evento clic en el boton de Registrarse en la app*/
    public void clicRegistrarse(View view){

        startActivity(new Intent(MainActivity.this,RegisterActivity.class));

    }

}