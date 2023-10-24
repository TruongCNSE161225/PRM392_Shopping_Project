package com.example.prm392_shopping_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prm392_shopping_project.database.CategoryDB;
import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.fragment.ProductFragment;
import com.example.prm392_shopping_project.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {
    ProductDB productDB;
    CategoryDB categoryDB;
    EditText editTextName, editTextDescription, editTextPrice, editTextUnit, editTextQuantity, editTextDiscount;
    ImageView imageView;
    Spinner spinnerCategory;
    int idCategory;
    Button buttonAdd, buttonUploadImage, buttonCancel;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        productDB = new ProductDB(this);
        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextUnit = findViewById(R.id.editTextUnit);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextDiscount = findViewById(R.id.editTextDiscount);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        imageView = findViewById(R.id.imageView);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUploadImage = findViewById(R.id.buttonUploadImage);
        buttonCancel = findViewById(R.id.buttonCancel);
        categoryDB = new CategoryDB(this);
        List<String> listNameCategory = categoryDB.getNameCategory();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listNameCategory);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                idCategory = categoryDB.getIdbyName(listNameCategory.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AddProductActivity.this, "You need select Category", Toast.LENGTH_LONG).show();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String name = editTextName.getText().toString();
                    String description = editTextDescription.getText().toString();
                    Double price = Double.valueOf(editTextPrice.getText().toString());
                    int quantity = Integer.parseInt(editTextQuantity.getText().toString());
                    String unit = editTextUnit.getText().toString();
                    int discount = Integer.parseInt(editTextDiscount.getText().toString());
                    byte[] imgUrl = imageViewToByte(imageView);
                    byte[] bigImgUrl = imageViewToByte(imageView);
                    Product p = new Product(name, description, price, quantity, unit, idCategory, discount, imgUrl, bigImgUrl);
                    productDB.insert(p);
                    Toast.makeText(AddProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    editTextName.setText("");
                    editTextDescription.setText("");
                    editTextPrice.setText("");
                    editTextUnit.setText("");
                    editTextQuantity.setText("");
                    editTextDiscount.setText("");
                    imageView.setImageResource(R.drawable.profile_picture);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(AddProductActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProductActivity.this, ProductFragment.class);
                startActivity(intent);
            }
        });

    }

    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, "Ban chua mo quyen", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}