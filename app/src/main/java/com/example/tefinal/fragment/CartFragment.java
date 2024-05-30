package com.example.tefinal.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tefinal.adapter.CartAdapter;
import com.example.tefinal.activity.PaymentActivity;
import com.example.tefinal.models.Product;
import com.example.tefinal.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Product> cartItems;
    private TextView textViewCartEmpty;
    private Button buttonCheckout;
    private ImageView addToPayment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_cart);
        textViewCartEmpty = view.findViewById(R.id.text_view_cart_empty);
        buttonCheckout = view.findViewById(R.id.button_checkout);
        addToPayment = view.findViewById(R.id.addtopayment);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inisialisasi cartItems dari SharedPreferences
        loadDataFromSharedPreferences();

        cartAdapter = new CartAdapter(getContext(), cartItems);
        recyclerView.setAdapter(cartAdapter);

        cartAdapter.setOnRemoveItemClickListener(new CartAdapter.OnRemoveItemClickListener() {
            @Override
            public void onRemoveItemClick(int position) {
                removeItem(position);
            }
        });

        updateCartVisibility();

        addToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItems.isEmpty()) {
                    Toast.makeText(getContext(), "isi keranjang dulu baru bisa bayar", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), PaymentActivity.class);
                    intent.putParcelableArrayListExtra("cartItems", new ArrayList<>(cartItems));
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private void loadDataFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("cart", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("cartItems", "");

        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>() {}.getType();
        cartItems = gson.fromJson(json, type);

        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
    }

    private void removeItem(int position) {
        if (cartItems != null && position >= 0 && position < cartItems.size()) {
            cartItems.remove(position);
            cartAdapter.notifyDataSetChanged(); // Memperbarui tampilan RecyclerView setelah menghapus item
            saveDataToSharedPreferences(); // Menyimpan perubahan ke SharedPreferences
            updateCartVisibility(); // Memperbarui visibilitas tampilan cart
        }
    }

    private void saveDataToSharedPreferences() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("cart", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(cartItems);

        editor.putString("cartItems", json);
        editor.apply();
    }

    private void updateCartVisibility() {
        if (cartItems.isEmpty()) {
            textViewCartEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            buttonCheckout.setVisibility(View.GONE);
        } else {
            textViewCartEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            buttonCheckout.setVisibility(View.VISIBLE);
        }
    }
}
