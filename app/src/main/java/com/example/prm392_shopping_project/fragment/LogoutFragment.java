package com.example.prm392_shopping_project.fragment;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_shopping_project.LoginActivity;

public class LogoutFragment extends AppCompatActivity {
    public void onClick(View view) {
        Intent i = new Intent(LogoutFragment.this, LoginActivity.class);
        startActivity(i);
    }
}