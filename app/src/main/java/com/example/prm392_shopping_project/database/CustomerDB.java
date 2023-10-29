package com.example.prm392_shopping_project.database;

import static com.example.prm392_shopping_project.database.DatabaseConfig.ACCOUNT_TABLE;
import static com.example.prm392_shopping_project.database.DatabaseConfig.CUSTOMER_TABLE;
import static com.example.prm392_shopping_project.database.DatabaseConfig.PRODUCT_TABLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.prm392_shopping_project.model.Customer;
import com.example.prm392_shopping_project.model.Order;
import com.example.prm392_shopping_project.model.Product;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CustomerDB extends AppDatabaseContext implements IGenericDB<Customer> {
    public CustomerDB(@Nullable Context context) {
        super(context);
    }

    @Override
    public long insert(Customer customer) {
        SQLiteDatabase db = super.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", customer.getName());
        values.put("email", customer.getEmail());
        values.put("phone", customer.getPhone());
        values.put("address", customer.getAddress());
        long count = db.insert(CUSTOMER_TABLE, null, values);
        return count;
    }

    public int getMaxId() {
        SQLiteDatabase db = super.getReadableDatabase();
        String sql = "SELECT * FROM " + CUSTOMER_TABLE + " ORDER BY id DESC LIMIT 1";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            try {
                int a = cursor.getInt(0);
                return a;
            } catch (Exception ex) {
                throw new RuntimeException();
            }
        }
        db.close();
        return 0;

    }


    @Override
    public long update(Customer customer) {
        return 0;
    }

    @Override
    public long delete(int id) {
        return 0;
    }

    @Override
    public Customer getById(int id) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        String query = "SELECT * FROM Customers";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String email = cursor.getString(1);
            String name = cursor.getString(2);
            String address = cursor.getString(3);
            String phone = cursor.getString(4);
            Customer customer = new Customer(id, email, name, address, phone);
            list.add(customer);
        }
        return list;
    }

    @Override
    public long seedingData() {
        return 0;
    }

    public List<Customer> getByName(String s) {
        List<Customer> list = new ArrayList<>();
        String query = "SELECT * FROM " + CUSTOMER_TABLE + " where name like '%" + s + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            Customer customer = new Customer(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            list.add(customer);
        }
        return list;
    }
}
