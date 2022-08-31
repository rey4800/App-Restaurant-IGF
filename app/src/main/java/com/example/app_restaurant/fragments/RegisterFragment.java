package com.example.app_restaurant.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.app_restaurant.R;


public class RegisterFragment extends Fragment {

    private Button buttonRegistrarse;
    private TextView txtInicarSesion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);



        //Inicalization of variables
        buttonRegistrarse = view.findViewById(R.id.btnRegistrarse);
        txtInicarSesion = view.findViewById(R.id.txtIniciarSesion);


        buttonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



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
}