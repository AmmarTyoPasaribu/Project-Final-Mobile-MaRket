package com.example.tefinal.api;

import com.example.tefinal.response.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("products")
    Call<ProductResponse> getProducts();
}