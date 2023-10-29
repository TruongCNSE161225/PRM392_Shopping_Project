package com.example.prm392_shopping_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView imageViewHomeMenu;
    NavigationView navigationViewHome;
    NavController navController;
    TextView textViewHomeMenu, textViewHomeUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawable);
        imageViewHomeMenu = findViewById(R.id.imageViewHomeMenu);
        navigationViewHome = findViewById(R.id.navigationViewHome);

        imageViewHomeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationViewHome.setItemIconTintList(null);
        navController = Navigation.findNavController(this, R.id.navigationHostFragmentHome);
        NavigationUI.setupWithNavController(navigationViewHome, navController);
        textViewHomeMenu = findViewById(R.id.textViewHomeMenu);
        View headerView = navigationViewHome.getHeaderView(0);

        Intent intent = getIntent();
        String username = (String) intent.getSerializableExtra("username");
        textViewHomeUsername = headerView.findViewById(R.id.textViewHomeUsername);
        textViewHomeUsername.setText(username);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                textViewHomeMenu.setText(navDestination.getLabel());
            }
        });
    }
}