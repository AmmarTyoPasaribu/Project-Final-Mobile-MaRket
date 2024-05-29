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
import com.example.tefinal.Product;
import com.example.tefinal.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textTitle.setText(product.getTitle());

        String description = product.getDescription();
        if (description.length() > 45) {
            description = description.substring(0, 35) + "...";
        }
        holder.textDescription.setText(description);

        holder.textPrice.setText("$" + product.getPrice());
        holder.textRating.setText(String.valueOf(product.getRating()));

        Glide.with(context)
                .load(product.getThumbnail())
                .into(holder.imageThumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(product);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageThumbnail;
        TextView textTitle, textDescription, textPrice, textRating;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageThumbnail = itemView.findViewById(R.id.image_thumbnail);
            textTitle = itemView.findViewById(R.id.text_title);
            textDescription = itemView.findViewById(R.id.text_description);
            textPrice = itemView.findViewById(R.id.text_price);
            textRating = itemView.findViewById(R.id.text_view_rating);
        }
    }
}
