package com.example.prm392_shopping_project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    List<Product> listProduct;
    private Context context;
    private ProductListener ProductListener;

    public ProductAdapter(Context context, List<Product> products) {
        this.listProduct = products;
        this.context = context;
    }

    public ProductAdapter(ProductAdapter.ProductListener productListener) {
        ProductListener = productListener;
    }

    public ProductListener getProductListener() {
        return ProductListener;
    }

    public Product getProductAt(int position) {
        return listProduct.get(position);
    }

    public void setProductListener(ProductAdapter.ProductListener productListener) {
        ProductListener = productListener;
    }

    public void setList(List<Product> list) {
        this.listProduct = list;
        notifyDataSetChanged(); // refesh
    }

    public ProductAdapter(List<Product> products) {
        this.listProduct = products;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_product, parent, false);
        return new ProductHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        final Product p = listProduct.get(position);
        byte[] Image = p.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
        holder.imageViewProduct.setImageBitmap(bitmap);
        holder.textViewProductName.setText(p.getName());
        holder.textViewProductDescription.setText(p.getDescription());
        holder.textViewProductPrice.setText(String.valueOf(p.getPrice()));
        holder.textViewProductUnit.setText(p.getUnit());
        holder.textViewProductQuantity.setText(String.valueOf(p.getQuantity()));
        holder.textViewProductDiscount.setText(String.valueOf(p.getDiscount()));
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewProduct;
        TextView textViewProductName, textViewProductDescription, textViewProductPrice, textViewProductUnit, textViewProductQuantity, textViewProductDiscount;
        CardView cardViewProductLayout;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductDescription = itemView.findViewById(R.id.textViewProductDescription);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            textViewProductQuantity = itemView.findViewById(R.id.textViewProductQuantity);
            textViewProductUnit = itemView.findViewById(R.id.textViewProductUnit);
            textViewProductDiscount = itemView.findViewById(R.id.textViewProductDiscount);
            cardViewProductLayout = itemView.findViewById(R.id.cardViewProductLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (ProductListener != null) {
                ProductListener.onItemClick(view, getAdapterPosition());
            }
        }

    }

    public interface ProductListener {
        void onItemClick(View view, int position);

    }
}


