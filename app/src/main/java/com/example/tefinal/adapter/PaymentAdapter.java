package com.example.tefinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.tefinal.models.Product;
import com.example.tefinal.R;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private Context context;
    private ArrayList<Product> cartItems;

    public PaymentAdapter(Context context, ArrayList<Product> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Product product = cartItems.get(position);
        holder.textTitle.setText(product.getTitle());
        holder.textPrice.setText("$" + product.getPrice());

        // Load thumbnail image using Glide library
        Glide.with(context)
                .load(product.getThumbnail())
                .placeholder(R.drawable.placeholder_image) // Placeholder image if thumbnail is not available
                .into(holder.imageThumbnail);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textPrice;
        ImageView imageThumbnail;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_payment_title);
            textPrice = itemView.findViewById(R.id.text_payment_price);
            imageThumbnail = itemView.findViewById(R.id.image_payment_thumbnail);
        }
    }
}
