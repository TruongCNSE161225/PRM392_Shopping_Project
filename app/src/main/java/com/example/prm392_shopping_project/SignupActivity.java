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
import com.example.prm392_shopping_project.model.Account;

import java.sql.Date;

public class SignupActivity extends AppCompatActivity {

    EditText phone, password, confirmPassword;
    Button signup;
    TextView login;
    AccountDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        phone = findViewById(R.id.editTextPhone);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextConfirmPassword);
        db = new AccountDB(this);
        signup = findViewById(R.id.buttonSignup);
        login = findViewById(R.id.textViewLogin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPhone = phone.getText().toString();
                String userPassword = password.getText().toString();
                String confirmPass = confirmPassword.getText().toString();

                if (TextUtils.isEmpty(userPhone) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(confirmPass)) {
                    Toast.makeText(SignupActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else if (!userPassword.equals(confirmPass)) {
                    Toast.makeText(SignupActivity.this, "Mật khẩu và xác nhận mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                } else if (db.checkPhone(userPhone)) {
                    Toast.makeText(SignupActivity.this, "Tài khoản đã tồn tại trên hệ thống", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isAdmin = true;
                    Date currentDate = new java.sql.Date(System.currentTimeMillis());
                    Account account = new Account(userPhone, userPassword, currentDate, isAdmin);

                    long result = db.insert(account);
                    if (result != -1) {
                        Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignupActivity.this, "Đăng ký thất bại, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
