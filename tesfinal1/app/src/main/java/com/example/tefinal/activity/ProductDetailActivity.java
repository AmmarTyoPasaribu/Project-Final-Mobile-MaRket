package com.example.tefinal.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tefinal.Product;
import com.example.tefinal.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView backbutton;
    private LinearLayout buttonAddToCart;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        buttonAddToCart = findViewById(R.id.button_add_to_cart);
        backbutton = findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Menutup aktivitas saat ini setelah berpindah ke MainActivity
            }
        });

        // Mendapatkan data produk dari Intent
        product = getIntent().getParcelableExtra("product");

        // Menampilkan informasi detail produk
        if (product != null) {
            ImageView imageViewThumbnail = findViewById(R.id.image_view_thumbnail);
            TextView textViewTitle = findViewById(R.id.text_view_title);
            TextView textViewDescription = findViewById(R.id.text_view_description);
            TextView textViewPrice = findViewById(R.id.text_view_price);
            TextView textViewDiscountPercentage = findViewById(R.id.text_view_discount_percentage);
            TextView textViewRating = findViewById(R.id.text_view_rating);
            TextView textViewStock = findViewById(R.id.text_view_stock);
            TextView textViewBrand = findViewById(R.id.text_view_brand);
            TextView textViewCategory = findViewById(R.id.text_view_category);

            Glide.with(this).load(product.getThumbnail()).into(imageViewThumbnail);
            textViewTitle.setText(product.getTitle());
            textViewDescription.setText(product.getDescription());
            textViewPrice.setText("$" + product.getPrice());
            textViewDiscountPercentage.setText(product.getDiscountPercentage() + "%");
            textViewRating.setText(" " + product.getRating());
            textViewStock.setText(" " + product.getStock());
            textViewBrand.setText(" " + product.getBrand());
            textViewCategory.setText("  " + product.getCategory());
        }

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(product);
            }
        });
    }

    private void addToCart(Product product) {
        SharedPreferences sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        String json = sharedPreferences.getString("cartItems", "");
        Type type = new TypeToken<List<Product>>() {}.getType();
        List<Product> cartItems = gson.fromJson(json, type);

        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        cartItems.add(product);

        String updatedJson = gson.toJson(cartItems);
        editor.putString("cartItems", updatedJson);
        editor.apply();

        Toast.makeText(ProductDetailActivity.this, "Produk ditambahkan ke keranjang", Toast.LENGTH_SHORT).show();
    }
}