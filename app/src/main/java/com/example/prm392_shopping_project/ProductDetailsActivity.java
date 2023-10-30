package com.example.prm392_shopping_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_shopping_project.model.Cart;
import com.example.prm392_shopping_project.model.Product;
import com.nex3z.notificationbadge.NotificationBadge;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView imageViewProductDetailsImage, imageViewProductDetailsBack, imageViewProductDetailsCart;
    TextView textViewProductDetailsName, textViewProductDetailsPrice, textViewProductDetailsDescription, textViewProductDetailsQuantity, textViewProductDetailsUnit;
    Button buttonProductDetailsAddToCart;
    String name, price, description, quantity, unit;
    int id;
    double priceCart;
    byte[] image;
    NotificationBadge notificationBadgeProductDetails;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent i = getIntent();


        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Product pro = (Product) bundle.get("object_product");
        init();

        byte[] img = (byte[]) pro.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imageViewProductDetailsImage.setImageBitmap(bitmap);

        id = pro.getId();
        name = pro.getName();
        price = pro.getPrice() + " $";
        priceCart = pro.getPrice();
        description = pro.getDescription();
        quantity = pro.getQuantity() + " ";
        unit = pro.getUnit();

        textViewProductDetailsName.setText(name);
        textViewProductDetailsPrice.setText(price);
        textViewProductDetailsDescription.setText(description);
        textViewProductDetailsQuantity.setText(quantity);
        textViewProductDetailsUnit.setText(unit);


        if (MainActivity.cartList.size() > 0) {
            notificationBadgeProductDetails.setText(String.valueOf(MainActivity.cartList.size()));
        }

        imageViewProductDetailsCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductDetailsActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
        imageViewProductDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductDetailsActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        buttonProductDetailsAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.cartList.size() > 0) {
                    Cart productAdd = new Cart(id, name, priceCart, unit, 1, img);
                    boolean flag = false;
                    for (int i = 0; i < MainActivity.cartList.size(); i++) {
                        if (MainActivity.cartList.get(i).getName().equals(productAdd.getName())) {
                            MainActivity.cartList.get(i).setQuantity(MainActivity.cartList.get(i).getQuantity() + 1);
                            flag = true;
                        }
                    }
                    if (!flag) {
                        MainActivity.cartList.add(productAdd);
                    }
                } else {
                    MainActivity.cartList.add(new Cart(id, name, priceCart, unit, 1, img));
                }
                notificationBadgeProductDetails.setText(String.valueOf(MainActivity.cartList.size()));
                Intent i = new Intent(ProductDetailsActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
    }

    public void init() {
        textViewProductDetailsName = findViewById(R.id.textViewProductDetailsName);
        textViewProductDetailsDescription = findViewById(R.id.textViewProductDetailsDescription);
        textViewProductDetailsPrice = findViewById(R.id.textViewProductDetailsPrice);
        imageViewProductDetailsImage = findViewById(R.id.imageViewProductDetailsImage);
        imageViewProductDetailsBack = findViewById(R.id.imageViewProductDetailsBack);
        textViewProductDetailsQuantity = findViewById(R.id.textViewProductDetailsQuantity);
        textViewProductDetailsUnit = findViewById(R.id.textViewProductDetailsUnit);
        buttonProductDetailsAddToCart = findViewById(R.id.buttonProductDetailsAddToCart);
        imageViewProductDetailsCart = findViewById(R.id.imageViewProductDetailsCart);
        notificationBadgeProductDetails = findViewById(R.id.notificationBadgeProductDetails);
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
                imageViewProductDetailsImage.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
