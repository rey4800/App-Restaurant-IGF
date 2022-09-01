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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class LoginFragment extends Fragment {


   private Button buttonIniciarSesion;
   private TextView txtRegistrarse;
   private String email = "";
   private String password = "";
   private EditText txtEmail,txtPassword;
   FirebaseAuth mAuth;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        //Inicalization of variables
       buttonIniciarSesion = view.findViewById(R.id.btnInicarSesion);
       txtRegistrarse = view.findViewById(R.id.txtRegistrarse);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPassword = view.findViewById(R.id.txtPassword);


        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){

                    loginUser();

                }else{

                    Toast.makeText(getActivity(), "Complete los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });


        txtRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.frame1, RegisterFragment.class, null);
                transaction.commit();


            }
        });


        return view;
    }

//METODOS

    private void loginUser() {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setReorderingAllowed(true);
                    transaction.replace(R.id.frame1,   ProfileFragment.class, null);
                    transaction.commit();

                }else{

                    Toast.makeText(getActivity(), "No se pudo iniciar sesion compruebe los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}