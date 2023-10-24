package com.example.prm392_shopping_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_shopping_project.database.CustomerDB;
import com.example.prm392_shopping_project.database.OrderDB;
import com.example.prm392_shopping_project.database.OrderDetailDB;
import com.example.prm392_shopping_project.model.Customer;
import com.example.prm392_shopping_project.model.Order;
import com.example.prm392_shopping_project.model.OrderDetail;

import java.sql.Date;

public class BuyConfirmActivity extends AppCompatActivity {

    EditText editTextName, editTextPhone, editTextEmail, editTextAddress;
    TextView textViewOrder;
    CustomerDB customerDB;
    OrderDB orderDB;
    OrderDetailDB orderDetailDB;
    String totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_confirm);
        Intent i = getIntent();

        totalPrice = i.getStringExtra("totalPrice");
        init();

        textViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String email = editTextEmail.getText().toString();
                String address = editTextAddress.getText().toString();
                if (name.equals("") || phone.equals("") || email.equals("") || address.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Xin vui lòng nhập đầy đủ thông tin.");
                    builder.show();

                } else {
                    long millis = System.currentTimeMillis();
                    Date date = new Date(millis);
                    Customer cus = new Customer(name, email, address, phone);
                    long a = customerDB.insert(cus);
                    int cusId = customerDB.getMaxId();
                    long saveOrder = orderDB.insert(new Order(cusId, date, Double.parseDouble(totalPrice)));
                    int orderId = orderDB.getMaxId();
                    for (int j = 0; j < MainActivity.cartList.size(); j++) {
                        orderDetailDB.insert(new OrderDetail(orderId, MainActivity.cartList.get(j).getId(), MainActivity.cartList.get(j).getPrice(), MainActivity.cartList.get(j).getQuantity()));
                    }
                    Toast.makeText(getApplicationContext(), "Đặt đơn hàng thành công", Toast.LENGTH_SHORT).show();
                    MainActivity.cartList.clear();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }


            }
        });
    }

    public void init() {
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        textViewOrder = findViewById(R.id.textViewOrder);
        customerDB = new CustomerDB(this);
        orderDB = new OrderDB(this);
        orderDetailDB = new OrderDetailDB(this);
    }
}