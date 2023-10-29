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

import com.example.prm392_shopping_project.AddProductActivity;
import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.ViewDetailProductActivity;
import com.example.prm392_shopping_project.adapter.ProductAdapter;
import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment implements ProductAdapter.ProductListener {
    RecyclerView recyclerViewFragmentProduct;
    ProductAdapter productAdapter;
    ProductDB db;
    List<Product> productList;
    FloatingActionButton floatingActionButtonFragmentProduct;
    SearchView searchViewFragmentProduct;

    public ProductFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_product, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new ProductDB((getContext()));
        floatingActionButtonFragmentProduct = view.findViewById(R.id.floatingActionButtonFragmentProduct);
        recyclerViewFragmentProduct = view.findViewById(R.id.recyclerViewFragmentProduct);
        searchViewFragmentProduct = view.findViewById(R.id.searchViewFragmentProduct);

        productList = new ArrayList<>();
        productList = db.getAll();

        floatingActionButtonFragmentProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                startActivity(intent);
            }
        });
        productAdapter = new ProductAdapter(productList);
        productAdapter.setProductListener(ProductFragment.this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewFragmentProduct.setLayoutManager(manager);
        recyclerViewFragmentProduct.setAdapter(productAdapter);
        searchViewFragmentProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Product> list = db.getByName(s);
                productAdapter.setList(list);
                return true;
            }
        });
    }

    public void onItemClick(View view, int position) {
        Product product = productAdapter.getProductAt(position);
        Intent intent = new Intent(getContext(), ViewDetailProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_product", product);
        intent.putExtras(bundle);
        getContext().startActivity(intent);

    }
}
