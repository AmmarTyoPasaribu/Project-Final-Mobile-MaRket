package com.example.tefinal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tefinal.Product;
import com.example.tefinal.R;

import java.util.List;

public class ConfirmedProductAdapter extends RecyclerView.Adapter<ConfirmedProductAdapter.ViewHolder> {

    private List<Product> confirmedProducts;

    public ConfirmedProductAdapter(List<Product> confirmedProducts) {
        this.confirmedProducts = confirmedProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirmed_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = confirmedProducts.get(position);
        holder.textViewTitle.setText(product.getTitle());
        holder.textViewDescription.setText(product.getDescription());
        holder.textViewPrice.setText("$" + product.getPrice());
        holder.textViewRating.setText("" + product.getRating());
        Glide.with(holder.itemView.getContext()).load(product.getThumbnail()).into(holder.imageViewThumbnail);
    }

    @Override
    public int getItemCount() {
        return confirmedProducts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewThumbnail;
        TextView textViewTitle, textViewDescription, textViewPrice, textViewRating;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewThumbnail = itemView.findViewById(R.id.image_view_thumbnail);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPrice = itemView.findViewById(R.id.text_view_price);
            textViewRating = itemView.findViewById(R.id.text_view_rating);
        }
    }
}
