package com.example.prm392_shopping_project.database;

import static com.example.prm392_shopping_project.database.DatabaseConfig.ACCOUNT_TABLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.prm392_shopping_project.model.Account;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AccountDB extends AppDatabaseContext implements IGenericDB<Account> {
    public AccountDB(@Nullable Context context) {
        super(context);
    }

    @Override
    public long insert(Account account) {
        SQLiteDatabase db = super.getWritableDatabase();
        String isAdmin;
        ContentValues values = new ContentValues();
        values.put("phone", account.getPhone());
        values.put("password", account.getPassword());
        values.put("date_created", account.getCreatedAt() + "");
        if (account.isAdmin() == false) {
            isAdmin = "0";
        } else {
            isAdmin = "1";
        }
        values.put("is_admin", isAdmin);
        long count = db.insert(ACCOUNT_TABLE, null, values);
        return count;
    }

    @Override
    public long update(Account account) {
        return 0;
    }

    @Override
    public long delete(int id) {
        return 0;
    }

    @Override
    public Account getById(int id) {
        return null;
    }

    public boolean isAdmin(String phone) {
        String query = "SELECT * FROM Accounts WHERE phone = '" + phone + "'";
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean isAdmin = false;
        if (cursor.moveToFirst()) {
            int isAdmin_temp = cursor.getInt(3);
            if (isAdmin_temp == 0) {
                isAdmin = false;
            } else {
                isAdmin = true;
            }
        }
        return isAdmin;
    }

    @Override
    public List<Account> getAll() {
        List<Account> list = new ArrayList<>();
        String query = "SELECT * FROM Accounts";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {

            String phone = cursor.getString(0);
            String pass = cursor.getString(1);
            Date c = new Date(cursor.getLong(2));
            int isAdmin_temp = cursor.getInt(3);
            boolean isAdmin;
            if (isAdmin_temp == 0) {
                isAdmin = false;
            } else {
                isAdmin = true;
            }
            Account account = new Account(phone, pass, c, isAdmin);
            list.add(account);
        }
        return list;
    }

    @Override
    public long seedingData() {
        long millis = System.currentTimeMillis();
        Date today = new Date(millis);
        long count = 0;
        Account a = new Account("0123", "abc123", today, false);
        Account b = new Account("012345", "abc123", today, false);
        Account c = new Account("01234", "abc123", today, true);
        count = insert(a);
        count = insert(b);
        count = insert(c);

        return count;
    }

    public Boolean checkPhone(String phone) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ACCOUNT_TABLE + " " +
                "where phone=?", new String[]{phone});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkPhonePassword(String phone, String password) {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ACCOUNT_TABLE + " " +
                "where phone=? and password = ?", new String[]{phone, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
