package com.example.prm392_shopping_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_shopping_project.database.AccountDB;

public class Login extends AppCompatActivity {

    EditText phone, password;
    Button login;
    AccountDB db;
    TextView signup;
    boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone = findViewById(R.id.editTextPhone);
        password = findViewById(R.id.editTextPassword);
        db = new AccountDB(this);
        login = findViewById(R.id.buttonLogin);
        signup = findViewById(R.id.textViewForSignup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPhone = phone.getText().toString();
                String userPass = password.getText().toString();
                isAdmin = db.isAdmin(userPhone);
                if (TextUtils.isEmpty(userPhone) || TextUtils.isEmpty(userPass)) {
                    Toast.makeText(Login.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkuserpass = db.checkLogin(userPhone, userPass);
                    if (checkuserpass == true) {

                        if (isAdmin) {
                            Toast.makeText(Login.this, "Quản trị đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("isAdmin", isAdmin);
                            intent.putExtra("username", userPhone);
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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        });
    }
}
