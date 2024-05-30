package com.example.tefinal.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tefinal.models.Product;
import com.example.tefinal.R;
import com.example.tefinal.adapter.PaymentAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView backbutton;
    private PaymentAdapter paymentAdapter;
    private List<Product> cartItems;
    private TextView textViewTotalUang, textViewTotalPrice;
    private Button buttonConfirmPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        recyclerView = findViewById(R.id.recycler_view_payment);
        textViewTotalUang = findViewById(R.id.text_view_total_uang);
        textViewTotalPrice = findViewById(R.id.text_view_total_price);
        buttonConfirmPayment = findViewById(R.id.button_confirm_payment);
        backbutton = findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Menutup aktivitas saat ini setelah berpindah ke MainActivity
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inisialisasi cartItems dari SharedPreferences
        loadDataFromSharedPreferences();

        // Load total_uang from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        float totalUang = sharedPreferences.getFloat("total_uang", 0.0f);
        textViewTotalUang.setText(String.format("Total Uang: $%.2f", totalUang));

        // Menghitung total harga dari semua produk yang ada di cart
        double totalPrice = calculateTotalPrice();
        textViewTotalPrice.setText(String.format("Total Price: $%.2f", totalPrice));

        // Menggunakan PaymentAdapter untuk menampilkan daftar produk di RecyclerView
        ArrayList<Product> cartItems = getIntent().getParcelableArrayListExtra("cartItems");
        paymentAdapter = new PaymentAdapter(this, cartItems);
        recyclerView.setAdapter(paymentAdapter);

        if (cartItems.isEmpty()) {
            TextView textViewEmptyCart = findViewById(R.id.text_view_empty_cart);
            textViewEmptyCart.setVisibility(View.VISIBLE);
        }

        // Menangani klik tombol "Confirm Payment"
        buttonConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to proceed with the payment?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmPayment();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void confirmPayment() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        float totalUang = sharedPreferences.getFloat("total_uang", 0.0f);
        double totalPrice = calculateTotalPrice();

        if (totalUang >= totalPrice) {
            totalUang -= totalPrice;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("total_uang", totalUang);
            editor.apply();

            // Simpan produk yang dikonfirmasi ke SharedPreferences
            saveConfirmedProductsToSharedPreferences(cartItems);

            // Menghapus semua item dari cart
            clearCart();

            // Menampilkan toast bahwa pembayaran berhasil
            Toast.makeText(this, "Payment success", Toast.LENGTH_SHORT).show();

            // Kembali ke MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Uang Gak Cukup", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveConfirmedProductsToSharedPreferences(List<Product> newConfirmedProducts) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Gson gson = new Gson();

        // Load existing confirmed products
        String existingProductsJson = sharedPreferences.getString("confirmedProducts", "");
        Type type = new TypeToken<List<Product>>() {}.getType();
        List<Product> existingConfirmedProducts = gson.fromJson(existingProductsJson, type);

        if (existingConfirmedProducts == null) {
            existingConfirmedProducts = new ArrayList<>();
        }

        // Add new confirmed products to the existing list
        existingConfirmedProducts.addAll(newConfirmedProducts);

        // Save the updated list back to SharedPreferences
        String updatedProductsJson = gson.toJson(existingConfirmedProducts);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("confirmedProducts", updatedProductsJson);
        editor.apply();
    }

    private void clearCart() {
        SharedPreferences sharedPreferences = getSharedPreferences("cart", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void loadDataFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("cart", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("cartItems", "");

        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>() {}.getType();
        cartItems = gson.fromJson(json, type);

        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
    }

    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Product product : cartItems) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }
}
