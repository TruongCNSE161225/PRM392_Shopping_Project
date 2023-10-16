package com.example.prm392_shopping_project;

import static com.example.prm392_shopping_project.R.drawable.ic_home_fish;
import static com.example.prm392_shopping_project.R.drawable.ic_home_fruits;
import static com.example.prm392_shopping_project.R.drawable.ic_home_meats;
import static com.example.prm392_shopping_project.R.drawable.ic_home_veggies;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
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
    RecyclerView discountRecyclerView, categoryRecyclerView, recentlyViewedRecycler;
    DiscountedProductAdapter discountedProductAdapter;
    List<Product> discountedProductsList;

    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    RecentlyViewedAdapter recentlyViewedAdapter;
    List<Product> recentlyViewedList;

    TextView allCategory, tv_product;
    ImageView cart, setting;
    NotificationBadge bage;

    public static List<Cart> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = new AppDatabaseContext(this);
        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        allCategory = findViewById(R.id.allCategoryImage);
        tv_product = findViewById(R.id.tv_product);
        recentlyViewedRecycler = findViewById(R.id.recently_item);
        cart = findViewById(R.id.cart_main);
        setting = findViewById(R.id.setting_main);
        bage = findViewById(R.id.badge_main);
        if (cartList != null) {
//            bage.setText(String.valueOf(cartList.size()));

        } else {
            cartList = new ArrayList<>();
//            bage.setText("0");

        }

        tv_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AllProductActivity.class);
                startActivity(i);
            }
        });

        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AllCategory.class);
                startActivity(i);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Login.class);
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
        discountRecyclerView.setLayoutManager(layoutManager);
        discountedProductAdapter = new DiscountedProductAdapter(this, dataList);
        discountRecyclerView.setAdapter(discountedProductAdapter);
    }


    private void setCategoryRecycler(List<Category> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, categoryDataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setRecentlyViewedRecycler(List<Product> recentlyViewedDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentlyViewedRecycler.setLayoutManager(layoutManager);
        recentlyViewedAdapter = new RecentlyViewedAdapter(this, recentlyViewedDataList);
        recentlyViewedRecycler.setAdapter(recentlyViewedAdapter);
    }


}