package com.example.prm392_shopping_project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> categoryList;
    private CategoriesListener categoriesListener;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_category_row, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category category = categoryList.get(position);
        byte[] Image = category.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
        holder.categoryImage.setImageBitmap(bitmap);


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public  class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView categoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.imageViewCategoryRow);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (categoriesListener != null) {
                categoriesListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    private interface CategoriesListener {
        void onItemClick(View view, int position);
    }
}
