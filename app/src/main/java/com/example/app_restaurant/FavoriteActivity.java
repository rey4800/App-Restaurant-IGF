package com.example.app_restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FavoriteActivity extends AppCompatActivity {



    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Inicializar variable menu
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        opcionSeleccionadaMenu();
    }



    public void opcionSeleccionadaMenu(){
        bottomNavigationView.setSelectedItemId(R.id.favoritos);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.favoritos:
                        return true;

                    case R.id.principal:
                        startActivity(new Intent(getApplicationContext(),MenuPrincipal.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.cuenta:
                        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

    }

}


