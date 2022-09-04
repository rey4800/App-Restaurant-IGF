package com.example.app_restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.app_restaurant.adapter.ListaRestaurantesAdapter;
import com.example.app_restaurant.fragments.LoginFragment;
import com.example.app_restaurant.fragments.ProfileFragment;
import com.example.app_restaurant.models.Coordenada;
import com.example.app_restaurant.models.Restaurante;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuPrincipal extends AppCompatActivity {
    List<Restaurante> restaurantes;
    private DatabaseReference mDatabase;
    private BottomNavigationView bottomNavigationView;
    private Button btnAgregarRestaurante;
    private  ListaRestaurantesAdapter listaRestaurante;
    RecyclerView recyclerView;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Inicializar variable menu
         bottomNavigationView = findViewById(R.id.bottom_navigation);
         btnAgregarRestaurante = findViewById(R.id.btnAgregar);
         recyclerView = findViewById(R.id.listRestaurantes);
         mAuth = FirebaseAuth.getInstance();
         opcionSeleccionadaMenu();
         cargarListaRestaurantes();



    }

    public void cargarListaRestaurantes(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        restaurantes = new ArrayList<>();
        listaRestaurante = new ListaRestaurantesAdapter(restaurantes, this);
        mDatabase = FirebaseDatabase.getInstance().getReference("Restaurantes");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                restaurantes.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Restaurante restaurante = dataSnapshot.getValue(Restaurante.class);
                    restaurantes.add(restaurante);
                }
                listaRestaurante.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listaRestaurante);
    }

    public void opcionSeleccionadaMenu(){
        bottomNavigationView.setSelectedItemId(R.id.principal);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.favoritos:
                        startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.principal:

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

    public void btnAgregarRestaurante(View view){


        if(mAuth.getCurrentUser() !=null){//usuario iniciSesion

            startActivity(new Intent(MenuPrincipal.this,AgregarRestauranteActivity.class));
            // finish();

        }else{

            Toast.makeText(this, "Debe de Iniciar Sesion para agregar un Restaurante", Toast.LENGTH_LONG).show();

        }


    }

}