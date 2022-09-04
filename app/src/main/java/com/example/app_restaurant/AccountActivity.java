package com.example.app_restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.app_restaurant.databinding.ActivityMainBinding;
import com.example.app_restaurant.fragments.LoginFragment;
import com.example.app_restaurant.fragments.ProfileFragment;
import com.example.app_restaurant.fragments.RegisterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_account);



        //atributos de ventana
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Inicializar variable menu
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        opcionSeleccionadaMenu();

        mAuth = FirebaseAuth.getInstance();


    }


    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() !=null){


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setReorderingAllowed(true);
            transaction.replace(R.id.frame1, new ProfileFragment());
            transaction.commit();

        }else{

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setReorderingAllowed(true);
            transaction.replace(R.id.frame1, new LoginFragment());
            transaction.commit();


        }


    }


    public void opcionSeleccionadaMenu(){
        bottomNavigationView.setSelectedItemId(R.id.cuenta);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.favoritos:
                    validarInicioFavoritos();
                        return true;

                    case R.id.principal:
                        startActivity(new Intent(getApplicationContext(),MenuPrincipal.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.cuenta:
                        return true;
                }
                return false;
            }
        });

    }

    public void validarInicioFavoritos(){

        if(mAuth.getCurrentUser() !=null){//usuario iniciSesion

            startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
            overridePendingTransition(0,0);
            finish();

        }else{

            Toast.makeText(this, "Debe de Iniciar Sesion para ver Favoritos", Toast.LENGTH_LONG).show();

        }

    }



}