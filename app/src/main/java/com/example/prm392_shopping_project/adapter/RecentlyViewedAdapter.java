package com.example.prm392_shopping_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.ProductDetailsActivity;
import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.model.Product;

import java.util.List;

public class RecentlyViewedAdapter extends RecyclerView.Adapter<RecentlyViewedAdapter.RecentlyViewedViewHolder> {
    Context context;
    List<Product> recentlyViewedList;
    private RecentlyViewedListener RecentlyViewedListener;

    public RecentlyViewedAdapter(Context context, List<Product> recentlyViewedList) {
        this.context = context;
        this.recentlyViewedList = recentlyViewedList;
    }

    public void setList(List<Product> list) {
        this.recentlyViewedList = list;
        notifyDataSetChanged();
    }

    public RecentlyViewedAdapter(RecentlyViewedAdapter.RecentlyViewedListener recentlyViewedListener) {
        RecentlyViewedListener = recentlyViewedListener;
    }

    public RecentlyViewedAdapter.RecentlyViewedListener getRecentlyViewedListener() {
        return RecentlyViewedListener;
    }

    public void setRecentlyViewedListener(RecentlyViewedAdapter.RecentlyViewedListener recentlyViewedListener) {
        RecentlyViewedListener = recentlyViewedListener;
    }

    public Product getrecentlyViewedAt(int position) {
        return recentlyViewedList.get(position);
    }


    @NonNull
    @Override
    public RecentlyViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_recently_viewed, parent, false);

        return new RecentlyViewedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyViewedViewHolder holder, int position) {

        Product product = recentlyViewedList.get(position);
        holder.textViewRecentlyViewedName.setText(product.getName());
        holder.textViewRecentlyViewedPrice.setText(product.getPrice() + "$/");
        holder.textViewRecentlyViewedUnit.setText(product.getUnit());
        holder.textViewRecentlyViewedDiscount.setText(String.valueOf(product.getDiscount()));
        byte[] Image = product.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
        holder.imageViewRecentlyViewed.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, ProductDetailsActivity.class);
                Product product = recentlyViewedList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_product", product);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recentlyViewedList.size();
    }

    public class RecentlyViewedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewRecentlyViewedName, textViewRecentlyViewedPrice,
                textViewRecentlyViewedDiscount, textViewRecentlyViewedUnit;
        ImageView imageViewRecentlyViewed;
        CardView cardViewRecentlyViewedLayout;

        public RecentlyViewedViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewRecentlyViewedName = itemView.findViewById(R.id.textViewRecentlyViewedName);
            textViewRecentlyViewedUnit = itemView.findViewById(R.id.textViewRecentlyViewedUnit);
            textViewRecentlyViewedPrice = itemView.findViewById(R.id.textViewRecentlyViewedPrice);
            textViewRecentlyViewedDiscount = itemView.findViewById(R.id.textViewRecentlyViewedDiscount);
            imageViewRecentlyViewed = itemView.findViewById(R.id.imageViewRecentlyViewed);
            cardViewRecentlyViewedLayout = itemView.findViewById(R.id.cardViewRecentlyViewedLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (RecentlyViewedListener != null) {
                RecentlyViewedListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface RecentlyViewedListener {
        void onItemClick(View view, int position);

    }
}
