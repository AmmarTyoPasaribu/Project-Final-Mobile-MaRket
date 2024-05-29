package com.example.tefinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.tefinal.Product;
import com.example.tefinal.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<Product> cartItems;
    private OnRemoveItemClickListener removeItemClickListener;

    public interface OnRemoveItemClickListener {
        void onRemoveItemClick(int position);
    }

    public CartAdapter(Context context, List<Product> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    public void setOnRemoveItemClickListener(OnRemoveItemClickListener listener) {
        this.removeItemClickListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItems.get(position);
        holder.textTitle.setText(product.getTitle());
        holder.textPrice.setText("$" + product.getPrice());

        // Load thumbnail image using Glide library
        Glide.with(context)
                .load(product.getThumbnail())
                .placeholder(R.drawable.placeholder_image) // Placeholder image jika thumbnail tidak tersedia
                .into(holder.imageThumbnail); // Assuming you have an ImageView for thumbnail

        holder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition(); // Mendapatkan posisi item di RecyclerView
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    // Menampilkan dialog konfirmasi
                    new AlertDialog.Builder(context)
                            .setTitle("Konfirmasi")
                            .setMessage("Yakin ingin batal pesan ni produk?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                if (removeItemClickListener != null) {
                                    removeItemClickListener.onRemoveItemClick(adapterPosition);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textPrice;
        ImageView imageThumbnail;
        ImageView buttonRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_cart_title);
            textPrice = itemView.findViewById(R.id.text_cart_price);
            imageThumbnail = itemView.findViewById(R.id.image_cart_thumbnail);
            buttonRemove = itemView.findViewById(R.id.button_remove_from_cart);
        }
    }
}
