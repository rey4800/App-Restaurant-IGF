package com.example.app_restaurant.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_restaurant.R;
import com.example.app_restaurant.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RegisterFragment extends Fragment {

    private Button buttonRegistrarse;
    private TextView txtInicarSesion;
    private EditText txtNombre,txtEmail,txtPassword, txtConfirmPassword;
    private User usuario;


    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

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
        View view = inflater.inflate(R.layout.fragment_register, container, false);


        //Inicalization of variables
        buttonRegistrarse = view.findViewById(R.id.btnRegistrarse);
        txtInicarSesion = view.findViewById(R.id.txtIniciarSesion);
        txtNombre = view.findViewById(R.id.txtNombre);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtConfirmPassword = view.findViewById(R.id.txtConfirmPassword);


        buttonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String nombre = txtNombre.getText().toString();
               String email = txtEmail.getText().toString();
               String password = txtPassword.getText().toString();
               String confirmPassword = txtConfirmPassword.getText().toString();
               Date currentTime = Calendar.getInstance().getTime();

                if(!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()){

                    if(validarCaracteresPassword(password)){

                        if (password.equals(confirmPassword)){ // password == confirmPassword

                            usuario.setNombre(nombre);
                            usuario.setPassword(password);
                            usuario.setEmail(email);
                            usuario.setPuntaje(0);
                            usuario.setId_tipo(2);
                            usuario.setCreated_at(currentTime.toString());
                            registrarUser();

                        }else{
                            Toast.makeText(getActivity(), "Las password deben ser iguales", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "El password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        txtInicarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.frame1, LoginFragment.class, null);
                transaction.commit();


            }
        });


        return view;
    }


    //METODOS

    private void registrarUser() {


        mAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    usuario.setId_Usu(mAuth.getCurrentUser().getUid());

                    mDatabase.child("Users").child(usuario.getId_Usu()).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if(task2.isSuccessful()){

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.setReorderingAllowed(true);
                                transaction.replace(R.id.frame1,   ProfileFragment.class, null);
                                transaction.commit();

                            }else{

                                Toast.makeText(getActivity(), "No se pudo registrar en base de datos", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                  }else{

                    Toast.makeText(getActivity(), "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }




    boolean validarCaracteresPassword(String password){

        if(password.length() >=6){
            return true;
        }else{
            return false;
        }
    }




}