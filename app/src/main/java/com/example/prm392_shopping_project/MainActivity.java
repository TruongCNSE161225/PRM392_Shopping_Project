package com.example.prm392_shopping_project;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_shopping_project.adapter.CategoryAdapter;
import com.example.prm392_shopping_project.adapter.DiscountedProductAdapter;
import com.example.prm392_shopping_project.adapter.RecentlyViewedAdapter;
import com.example.prm392_shopping_project.database.AccountDB;
import com.example.prm392_shopping_project.database.AppDatabaseContext;
import com.example.prm392_shopping_project.database.CategoryDB;
import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.model.Account;
import com.example.prm392_shopping_project.model.Cart;
import com.example.prm392_shopping_project.model.Category;
import com.example.prm392_shopping_project.model.Product;
import com.nex3z.notificationbadge.NotificationBadge;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AppDatabaseContext _context;
    AccountDB accountDB = new AccountDB(this);
    ProductDB productDB = new ProductDB(this);
    CategoryDB categoryDB = new CategoryDB(this);
    RecyclerView recyclerViewMainDiscount, recyclerViewMainCategory, recyclerViewMainItem;
    DiscountedProductAdapter discountedProductAdapter;
    List<Product> discountedProductsList;

    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    RecentlyViewedAdapter recentlyViewedAdapter;
    List<Product> recentlyViewedList;

    TextView textViewMainAllCategory, textViewMainProduct;
    ImageView imageViewMainCart, imageViewMainSetting;
    NotificationBadge notificationBadgeMainCart;

    public static List<Cart> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = new AppDatabaseContext(this);
        recyclerViewMainDiscount = findViewById(R.id.recyclerViewMainDiscount);
        recyclerViewMainCategory = findViewById(R.id.recyclerViewMainCategory);
        textViewMainAllCategory = findViewById(R.id.textViewMainAllCategory);
        textViewMainProduct = findViewById(R.id.textViewMainProduct);
        recyclerViewMainItem = findViewById(R.id.recyclerViewMainItem);
        imageViewMainCart = findViewById(R.id.imageViewMainCart);
        imageViewMainSetting = findViewById(R.id.imageViewMainSetting);
        notificationBadgeMainCart = findViewById(R.id.notificationBadgeMainCart);
        if (cartList != null) {
//            bage.setText(String.valueOf(cartList.size()));

        } else {
            cartList = new ArrayList<>();
//            bage.setText("0");

        }

        textViewMainProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AllProductActivity.class);
                startActivity(i);
            }
        });

        textViewMainAllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AllCategoryActivity.class);
                startActivity(i);
            }
        });
        imageViewMainCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
        imageViewMainSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        // seedingData
        List<Category> allCategory = categoryDB.getAll();
//        if (allCategory.size() == 0){
//            long count = categoryDB.seedingData();
//        }
        List<Product> allProduct = new ArrayList<>();
        allProduct = productDB.getAll();
//        if(allProduct.size() == 0){
//            productDB.seedingData();
//        }
        List<Account> listAccount = new ArrayList<>();
        listAccount = accountDB.getAll();
        if (listAccount.size() == 0) {
            accountDB.seedingData();
        }
        // adding data to model
        discountedProductsList = productDB.getDiscountProduct();

        // adding data to model
        categoryList = new ArrayList<Category>();
        categoryList = categoryDB.getAll();

        // adding data to model
        recentlyViewedList = productDB.getAll();

        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList);
        setRecentlyViewedRecycler(recentlyViewedList);

    }

    private void setDiscountedRecycler(List<Product> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMainDiscount.setLayoutManager(layoutManager);
        discountedProductAdapter = new DiscountedProductAdapter(this, dataList);
        recyclerViewMainDiscount.setAdapter(discountedProductAdapter);
    }


    private void setCategoryRecycler(List<Category> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMainCategory.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, categoryDataList);
        recyclerViewMainCategory.setAdapter(categoryAdapter);
    }

    private void setRecentlyViewedRecycler(List<Product> recentlyViewedDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMainItem.setLayoutManager(layoutManager);
        recentlyViewedAdapter = new RecentlyViewedAdapter(this, recentlyViewedDataList);
        recyclerViewMainItem.setAdapter(recentlyViewedAdapter);
    }


}