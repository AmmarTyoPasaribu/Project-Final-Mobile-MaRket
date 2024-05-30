package com.example.tefinal.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tefinal.api.ApiService;
import com.example.tefinal.models.Product;
import com.example.tefinal.adapter.ProductAdapter;
import com.example.tefinal.activity.ProductDetailActivity;
import com.example.tefinal.response.ProductResponse;
import com.example.tefinal.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements ProductAdapter.OnItemClickListener {

    private RecyclerView recyclerView;

    private EditText editTextSearch;
    private ProductAdapter productAdapter;
    private static final String BASE_URL = "https://dummyjson.com/";
    private SharedPreferences sharedPreferences;
    private List<Product> productList;
    private List<Product> filteredList;

    private ImageView imageViewError;
    private TextView textViewError;
    private Button buttonRetry;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        editTextSearch = view.findViewById(R.id.edit_text_search);
        imageViewError = view.findViewById(R.id.image_view_error);
        textViewError = view.findViewById(R.id.text_view_error);
        buttonRetry = view.findViewById(R.id.button_retry);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));





        productList = new ArrayList<>();
        filteredList = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(), filteredList);
        productAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(productAdapter);

        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });

        fetchData();

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void fetchData() {
        showLoading(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ProductResponse> call = apiService.getProducts();

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                showLoading(false);
                if (!response.isSuccessful()) {
                    showError(true);
                    Toast.makeText(getContext(), "Response not successful: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ProductResponse productResponse = response.body();
                if (productResponse != null) {
                    productList = productResponse.getProducts();
                    filterProducts("");
                    showError(false);
                } else {
                    showError(true);
                    Toast.makeText(getContext(), "Product response is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                showLoading(false);
                showError(true);
                Toast.makeText(getContext(), "Pastikan Berada DI Tempat Dengan Sinyal Yang Memadai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterProducts(String query) {
        filteredList.clear();
        for (Product product : productList) {
            if (product.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(product);
            }
        }
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Product product) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }


    private void saveLoginStatus(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        imageViewError.setVisibility(View.GONE);
        textViewError.setVisibility(View.GONE);
        buttonRetry.setVisibility(View.GONE);
    }

    private void showError(boolean isError) {
        imageViewError.setVisibility(isError ? View.VISIBLE : View.GONE);
        textViewError.setVisibility(isError ? View.VISIBLE : View.GONE);
        buttonRetry.setVisibility(isError ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isError ? View.GONE : View.VISIBLE);
    }
}
