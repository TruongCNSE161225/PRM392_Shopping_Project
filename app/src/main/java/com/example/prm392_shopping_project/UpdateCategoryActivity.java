package com.example.prm392_shopping_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_shopping_project.database.AccountDB;
import com.example.prm392_shopping_project.database.CategoryDB;
import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.model.Category;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

public class UpdateCategoryActivity extends AppCompatActivity {
    TextView textViewUpdateCategoryId;
    EditText editTextUpdateCategoryName;
    ImageView imageViewUpdateCategory;
    Button buttonUpdateCategory, buttonDeleteCategory, buttonUpdateCategoryImage;
    CategoryDB db;
    ProductDB pdb;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);
        pdb = new ProductDB(this);
        db = new CategoryDB(this);
        textViewUpdateCategoryId = findViewById(R.id.textViewUpdateCategoryId);
        editTextUpdateCategoryName = findViewById(R.id.editTextUpdateCategoryName);
        imageViewUpdateCategory = findViewById(R.id.imageViewUpdateCategory);
        buttonUpdateCategory = findViewById(R.id.buttonUpdateCategory);
        buttonDeleteCategory = findViewById(R.id.buttonDeleteCategory);
        buttonUpdateCategoryImage = findViewById(R.id.buttonUpdateCategoryImage);

        Intent intent = getIntent();
        String name = (String) intent.getSerializableExtra("name");
        int id = (int) intent.getSerializableExtra("id");
        byte[] img = (byte[]) intent.getSerializableExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

        imageViewUpdateCategory.setImageBitmap(bitmap);
        textViewUpdateCategoryId.setText(id + "");
        editTextUpdateCategoryName.setText(name);

        buttonUpdateCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(UpdateCategoryActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });

        buttonUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(textViewUpdateCategoryId.getText().toString());
                String name = editTextUpdateCategoryName.getText().toString();
                Category category = new Category(id, name, imageViewToByte(imageViewUpdateCategory));
                db.update(category);
                Toast.makeText(UpdateCategoryActivity.this, "Cập nhật thành công", Toast.LENGTH_LONG).show();
//                AccountDB db;
//                String userPhone = phone.getText().toString();
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                intent.putExtra("isAdmin", isAdmin);
//                intent.putExtra("username", userPhone);
//                startActivity(intent);
                Intent back = new Intent(UpdateCategoryActivity.this, HomeActivity.class);
                startActivity(back);
            }
        });

        buttonDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(textViewUpdateCategoryId.getText().toString());
                if (pdb.getProductByCategoryId(id) == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn xác nhận sẽ xoá loại sản phẩm này chứ?");
                    builder.setIcon(R.drawable.ic_delete);
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.delete(id);

                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(UpdateCategoryActivity.this, "Không thể xoá vì đang có sản phẩm hiện hành", Toast.LENGTH_SHORT).show();
                }
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
                imageViewUpdateCategory.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}