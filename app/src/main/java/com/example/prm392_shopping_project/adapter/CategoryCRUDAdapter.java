package com.example.prm392_shopping_project.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.model.Category;

import java.util.List;

public class CategoryCRUDAdapter extends RecyclerView.Adapter<CategoryCRUDAdapter.CategoryViewHolder> {
    private List<Category> list;
    private CategoryListener categoryListener;

    public CategoryCRUDAdapter(List<Category> list) {
        this.list = list;
    }

    public Category getCategoryAt(int position) {
        return list.get(position);
    }

    public void setCategoryListener(CategoryListener categoryListener) {
        this.categoryListener = categoryListener;
    }

    public void setList(List<Category> list) {
        this.list = list;
        notifyDataSetChanged(); // refesh
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_category_crud, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = list.get(position);
        byte[] Image = category.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
        holder.imageViewCategoryCRUD.setImageBitmap(bitmap);
        holder.textViewNameCategoryCRUD.setText(category.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewNameCategoryCRUD;
        private ImageView imageViewCategoryCRUD;

        public CategoryViewHolder(@NonNull View view) {
            super(view);
            textViewNameCategoryCRUD = view.findViewById(R.id.textViewNameCategoryCRUD);
            imageViewCategoryCRUD = view.findViewById(R.id.imageViewCategoryCRUD);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (categoryListener != null) {
                categoryListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface CategoryListener {
        void onItemClick(View view, int position);

    }
}
