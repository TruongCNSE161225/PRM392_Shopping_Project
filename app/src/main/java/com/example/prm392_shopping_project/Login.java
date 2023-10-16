package com.example.prm392_shopping_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prm392_shopping_project.database.AccountDB;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button login;
    AccountDB db;
    boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.editTextPhone);
        password = findViewById(R.id.editTextNumberPassword);
        db = new AccountDB(this);
        login = findViewById(R.id.buttonLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                isAdmin = db.isAdmin(user);
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(Login.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkuserpass = db.checkUserNamePassword(user, pass);
                    if (checkuserpass == true) {

                        if (isAdmin) {
                            Toast.makeText(Login.this, "Quản trị đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("isAdmin", isAdmin);
                            intent.putExtra("username", user);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Quý khách đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        }
                    } else {
                        Toast.makeText(Login.this, "Đăng nhập thất bại, vui lòng kiểm tra lại tài khoản", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}