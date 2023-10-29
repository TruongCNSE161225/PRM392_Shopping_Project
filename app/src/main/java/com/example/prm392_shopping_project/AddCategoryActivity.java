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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prm392_shopping_project.database.CategoryDB;
import com.example.prm392_shopping_project.fragment.CategoryFragment;
import com.example.prm392_shopping_project.fragment.ProductFragment;
import com.example.prm392_shopping_project.model.Category;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddCategoryActivity extends AppCompatActivity {
    EditText editTextAddCategoryName;
    ImageView imageViewAddCategory;
    Button buttonAddCategory, buttonAddCategoryUploadImage, buttonAddCategoryCancel;
    CategoryDB db;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        db = new CategoryDB(this);
        editTextAddCategoryName = findViewById(R.id.editTextAddCategoryName);
        imageViewAddCategory = findViewById(R.id.imageViewAddCategory);
        buttonAddCategory = findViewById(R.id.buttonAddCategory);
        buttonAddCategoryUploadImage = findViewById(R.id.buttonAddCategoryUploadImage);
        buttonAddCategoryCancel = findViewById(R.id.buttonAddCategoryCancel);

        buttonAddCategoryUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(AddCategoryActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });

        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db.insert(new Category(
                            editTextAddCategoryName.getText().toString().trim(),
                            imageViewToByte(imageViewAddCategory))
                    );
                    Toast.makeText(AddCategoryActivity.this, "Thêm loại sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCategoryActivity.this, CategoryFragment.class);
                    startActivity(intent);
                    editTextAddCategoryName.setText("");
                    imageViewAddCategory.setImageResource(R.drawable.profile_picture);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        buttonAddCategoryCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategoryActivity.this, CategoryFragment.class);
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
                Toast.makeText(this, "Bạn chưa mở quyền truy cập", Toast.LENGTH_SHORT).show();
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
                imageViewAddCategory.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}