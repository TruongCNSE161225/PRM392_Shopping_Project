package com.example.prm392_shopping_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.fragment.ProductFragment;
import com.example.prm392_shopping_project.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class UpdateProductActivity extends AppCompatActivity {
    EditText editTextUpdateProductName, editTextUpdateProductDescription, editTextUpdateProductPrice,
            editTextUpdateProductUnit, editTextUpdateProductQuantity, editTextUpdateProductDiscount;
    ImageView imageViewUpdateProduct;
    ProductDB db;
    Button buttonUpdateProduct, buttonUpdateProductUpdateImage;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Product pro = (Product) bundle.get("object_product");
        editTextUpdateProductName = findViewById(R.id.editTextUpdateProductName);
        editTextUpdateProductDescription = findViewById(R.id.editTextUpdateProductDescription);
        editTextUpdateProductPrice = findViewById(R.id.editTextUpdateProductPrice);
        editTextUpdateProductUnit = findViewById(R.id.editTextUpdateProductUnit);
        editTextUpdateProductQuantity = findViewById(R.id.editTextUpdateProductQuantity);
        editTextUpdateProductDiscount = findViewById(R.id.editTextUpdateProductDiscount);
        imageViewUpdateProduct = findViewById(R.id.imageViewUpdateProduct);
        buttonUpdateProduct = findViewById(R.id.buttonUpdateProduct);
        buttonUpdateProductUpdateImage = findViewById(R.id.buttonUpdateProductUpdateImage);

        editTextUpdateProductName.setText(pro.getName());
        editTextUpdateProductDescription.setText(pro.getDescription());
        editTextUpdateProductPrice.setText(String.valueOf(pro.getPrice()));
        editTextUpdateProductUnit.setText(pro.getUnit());
        editTextUpdateProductQuantity.setText(String.valueOf(pro.getQuantity()));
        editTextUpdateProductDiscount.setText(String.valueOf(pro.getDiscount()));
        byte[] img = (byte[]) pro.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imageViewUpdateProduct.setImageBitmap(bitmap);
        buttonUpdateProductUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(UpdateProductActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });

    }

    public void onUpdate(View view) {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Product pro = (Product) bundle.get("object_product");

        pro.setName(editTextUpdateProductName.getText().toString());
        pro.setDescription(editTextUpdateProductDescription.getText().toString());
        pro.setPrice(Double.valueOf(editTextUpdateProductPrice.getText().toString()));
        pro.setUnit(editTextUpdateProductUnit.getText().toString());
        pro.setQuantity(Integer.parseInt(editTextUpdateProductQuantity.getText().toString()));
        pro.setDiscount(Integer.parseInt(editTextUpdateProductDiscount.getText().toString()));

        byte[] imgUrl = imageViewToByte(imageViewUpdateProduct);
        byte[] bigImgUrl = imageViewToByte(imageViewUpdateProduct);
        pro.setImageUrl(imgUrl);

        db = new ProductDB(this);
        db.update(pro);
        Toast.makeText(UpdateProductActivity.this, "Cập nhật thành công", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(UpdateProductActivity.this, ProductFragment.class);
        startActivity(intent);
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
                imageViewUpdateProduct.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}