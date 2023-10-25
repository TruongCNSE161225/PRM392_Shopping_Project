package com.example.prm392_shopping_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.AddCategoryActivity;
import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.UpdateCategoryActivity;
import com.example.prm392_shopping_project.adapter.CategoryCRUDAdapter;
import com.example.prm392_shopping_project.database.CategoryDB;
import com.example.prm392_shopping_project.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment implements CategoryCRUDAdapter.CategoryListener{
    RecyclerView recyclerViewFragmentCategory;
    CategoryCRUDAdapter categoryCRUDAdapter;
    CategoryDB db;
    List<Category> categoryList;
    FloatingActionButton floatingActionButtonFragmentCategory;
    SearchView searchViewFragmentCategory;

    public CategoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new CategoryDB(getContext());
        floatingActionButtonFragmentCategory = view.findViewById(R.id.floatingActionButtonFragmentCategory);
        recyclerViewFragmentCategory = view.findViewById(R.id.recyclerViewFragmentCategory);
        searchViewFragmentCategory = view.findViewById(R.id.searchViewFragmentCategory);

        categoryList = new ArrayList<>();
        categoryList = db.getAll();

        floatingActionButtonFragmentCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
                startActivity(intent);
            }
        });
        categoryCRUDAdapter = new CategoryCRUDAdapter(categoryList);
        categoryCRUDAdapter.setCategoryListener(CategoryFragment.this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewFragmentCategory.setLayoutManager(manager);
        recyclerViewFragmentCategory.setAdapter(categoryCRUDAdapter);
        searchViewFragmentCategory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Category> list = db.getByName(s);
                categoryCRUDAdapter.setList(list);
                return true;
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Category category = categoryCRUDAdapter.getCategoryAt(position);
        Intent intent = new Intent(getActivity(), UpdateCategoryActivity.class);
        intent.putExtra("id", category.getId());
        intent.putExtra("name", category.getName());
        intent.putExtra("image", category.getImageUrl());
        startActivity(intent);

    }


}
