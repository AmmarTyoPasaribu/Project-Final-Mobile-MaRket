package com.example.tefinal.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tefinal.adapter.ConfirmedProductAdapter;
import com.example.tefinal.activity.LoginActivity;
import com.example.tefinal.models.Product;
import com.example.tefinal.activity.ProfileActivity;
import com.example.tefinal.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private TextView textViewUsername, textViewTotalUang;
    private ImageView buttonEditProfile;
    private ImageView tambahuang;
    private RecyclerView recyclerViewConfirmedProducts;
    private SharedPreferences sharedPreferences;
    private ConfirmedProductAdapter confirmedProductAdapter;
    private List<Product> confirmedProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textViewUsername = view.findViewById(R.id.text_view_username);
        textViewTotalUang = view.findViewById(R.id.text_view_total_uang);
        tambahuang = view.findViewById(R.id.tambah_uang);
        recyclerViewConfirmedProducts = view.findViewById(R.id.recycler_view_confirmed_products);

        buttonEditProfile = view.findViewById(R.id.button_edit_profile);
        ImageView buttonLogout = view.findViewById(R.id.button_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        tambahuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                naikuang();;
            }
        });




        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });

        // Load user data from SharedPreferences
        loadUserDataFromSharedPreferences();

        // Setup RecyclerView for confirmed products
        confirmedProducts = loadConfirmedProductsFromSharedPreferences();
        confirmedProductAdapter = new ConfirmedProductAdapter(confirmedProducts);
        recyclerViewConfirmedProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewConfirmedProducts.setAdapter(confirmedProductAdapter);

        return view;
    }

    private void loadUserDataFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        float totalUang = sharedPreferences.getFloat("total_uang", 0.0f);

        // Set username and total_uang to TextViews
        textViewUsername.setText("Username : " + username);
        textViewTotalUang.setText("Total Uang : $" + totalUang);
    }

    private List<Product> loadConfirmedProductsFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("confirmedProducts", "");
        Type type = new TypeToken<List<Product>>() {}.getType();
        List<Product> confirmedProducts = gson.fromJson(json, type);

        if (confirmedProducts == null) {
            confirmedProducts = new ArrayList<>();
        }

        return confirmedProducts;
    }

    private void logout() {
        saveLoginStatus(false);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
    private void saveLoginStatus(boolean isLoggedIn) {
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }

    private void naikuang() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        float totalUang = sharedPreferences.getFloat("total_uang", 0.0f);
        float total_uangbaru = totalUang + 200;
        textViewTotalUang.setText("Total Uang : $" + total_uangbaru);
        editor.putFloat("total_uang", total_uangbaru);
        editor.apply();
    }
}
