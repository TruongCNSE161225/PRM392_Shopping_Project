package com.example.prm392_shopping_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.AllProductActivity;
import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.model.Category;

import java.util.List;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.AllCategoryViewHolder> {

    Context context;
    List<Category> categoryList;
    private AllCategoriesListener allCategoriesListener;


    public AllCategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public AllCategoriesListener getAllCategoriesListener() {
        return allCategoriesListener;
    }

    public void setAllCategoriesListener(AllCategoriesListener allCategoriesListener) {
        this.allCategoriesListener = allCategoriesListener;
    }

    public Category getCategoryAt(int position) {
        return categoryList.get(position);
    }

    @NonNull
    @Override
    public AllCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_all_category_row, parent, false);

        return new AllCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoryViewHolder holder, int position) {

        Category category = categoryList.get(position);
        byte[] Image = category.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
        holder.categoryImage.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, AllProductActivity.class);
                i.putExtra("id", category.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class AllCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView categoryImage;

        public AllCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.imageViewAllCategoryRow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (allCategoriesListener != null) {
                allCategoriesListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface AllCategoriesListener {
        void onItemClick(View view, int position);
    }
}