package com.example.prm392_shopping_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.prm392_shopping_project.adapter.ProductListAdapter;
import com.example.prm392_shopping_project.database.CategoryDB;
import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.model.Product;

import java.util.List;

public class AllProductActivity extends AppCompatActivity implements ProductListAdapter.ProductListener {
    ProductDB db;
    RecyclerView recyclerViewAllProduct;
    ProductListAdapter productListAdapter;
    List<Product> allProductList;
    ImageView imageViewAllProductBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        db = new ProductDB(this);
        recyclerViewAllProduct = findViewById(R.id.recyclerViewAllProduct);

        Intent i = getIntent();
        int id = i.getIntExtra("id", 0);

        if (id != 0) allProductList = db.getByCateId(id);
        else allProductList = db.getAll();

        productListAdapter = new ProductListAdapter(allProductList);
        productListAdapter.setProductListener(this);
//        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerViewAllProduct.setLayoutManager(manager);
        recyclerViewAllProduct.setAdapter(productListAdapter);

        imageViewAllProductBack = findViewById(R.id.imageViewAllProductBack);

        imageViewAllProductBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(AllProductActivity.this, MainActivity.class);
                startActivity(back);
                finish();
            }
        });


    }

    @Override
    public void onItemClick(View view, int position) {
        Product p = productListAdapter.getProductAt(position);
        Intent i = new Intent(getApplicationContext(), ProductDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_product", p);
        i.putExtras(bundle);
        startActivity(i);
    }
}