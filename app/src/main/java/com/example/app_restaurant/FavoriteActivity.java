package com.example.app_restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.app_restaurant.adapter.ListaRestaurantesAdapter;
import com.example.app_restaurant.adapter.ListaRestaurantesAdapter2;
import com.example.app_restaurant.models.Restaurante;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {



    private BottomNavigationView bottomNavigationView;
    List<Restaurante> restaurantes;
    private DatabaseReference mDatabase;
    private ListaRestaurantesAdapter2 listaRestaurante2;
    RecyclerView recyclerView;
    private FirebaseAuth mAuth;

    //Declaracion de objetos para manipular las base de datos de firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Inicializar variable menu
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        recyclerView = findViewById(R.id.listRestaurantes);
        mAuth = FirebaseAuth.getInstance();

        //Instanciar los objetos de firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();//Obtenemos la refrencia de nuestra base de datos
        opcionSeleccionadaMenu();

        cargarListaRestaurantes();
    }



    public void cargarListaRestaurantes(){
        String idUsu = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        restaurantes = new ArrayList<>();
        listaRestaurante2 = new ListaRestaurantesAdapter2(restaurantes, this);
        DatabaseReference comment = databaseReference.child("Guardados").child(idUsu);


        comment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                restaurantes.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Restaurante restaurante = dataSnapshot.getValue(Restaurante.class);
                    restaurantes.add(restaurante);
                }
                listaRestaurante2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listaRestaurante2);
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


