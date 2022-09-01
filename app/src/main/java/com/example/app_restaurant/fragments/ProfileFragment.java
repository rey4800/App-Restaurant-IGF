package com.example.app_restaurant.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.app_restaurant.R;
import com.example.app_restaurant.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView txtNombre,txtEmail,txtPuntos, txtNombre2;
    private User usuario;
    Button buttonCerrarSesion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        usuario = new User();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txtNombre =  view.findViewById(R.id.textViewName);
        txtNombre2 =  view.findViewById(R.id.textName);
        txtEmail = view.findViewById(R.id.textViewEmail);
        txtPuntos = view.findViewById(R.id.textViewPuntos);
        buttonCerrarSesion = view.findViewById(R.id.btbCerrarSesion);

        getUserInfo();  //Obtenemos el usuario y el correo


        buttonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();        //Metodo para cerrar sesion en la app

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.frame1, LoginFragment.class, null);
                transaction.commit();

            }
        });


        return view;
    }



    private void getUserInfo(){
        String id = mAuth.getCurrentUser().getUid();

        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    usuario.setNombre(snapshot.child("nombre").getValue().toString());
                    usuario.setEmail(snapshot.child("email").getValue().toString());
                    usuario.setPuntaje(Integer.parseInt(snapshot.child("puntaje").getValue().toString()));

                    txtNombre.setText(usuario.getNombre());
                    txtNombre2.setText(usuario.getNombre());
                    txtEmail.setText(usuario.getEmail());
                    txtPuntos.setText(String.valueOf(usuario.getPuntaje()));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.e("Error database",error.toString());

            }
        });

    }




}