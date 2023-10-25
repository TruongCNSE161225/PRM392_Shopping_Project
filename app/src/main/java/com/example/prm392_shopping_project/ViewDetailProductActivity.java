package com.example.prm392_shopping_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.model.Product;

public class ViewDetailProductActivity extends AppCompatActivity {
    TextView textViewDetailProductName, textViewDetailProductDescription,
            textViewDetailProductPrice, textViewDetailProductUnit, textViewDetailProductQuantity;
    ImageView imageViewDetailProduct;
    ProductDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_product);
        db = new ProductDB(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Product pro = (Product) bundle.get("object_product");
        textViewDetailProductName = findViewById(R.id.textViewDetailProductName);
        textViewDetailProductDescription = findViewById(R.id.textViewDetailProductDescription);
        textViewDetailProductPrice = findViewById(R.id.textViewDetailProductPrice);
        textViewDetailProductUnit = findViewById(R.id.textViewDetailProductUnit);
        textViewDetailProductQuantity = findViewById(R.id.textViewDetailProductQuantity);
        imageViewDetailProduct = findViewById(R.id.imageViewDetailProduct);

        textViewDetailProductName.setText(pro.getName());
        textViewDetailProductDescription.setText(pro.getDescription());
        textViewDetailProductPrice.setText(String.valueOf(pro.getPrice()));
        textViewDetailProductUnit.setText(pro.getUnit());
        textViewDetailProductQuantity.setText(String.valueOf(pro.getQuantity()));
        byte[] img = (byte[]) pro.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imageViewDetailProduct.setImageBitmap(bitmap);
    }

    public void onUpdateProduct(View view) {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Product pro = (Product) bundle.get("object_product");
        Intent intent = new Intent(ViewDetailProductActivity.this, UpdateProductActivity.class);
        bundle.putSerializable("object_product", pro);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onDeleteProduct(View view) {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Product pro = (Product) bundle.get("object_product");
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn xác nhận muốn xoá sản phẩm này chứ?");
        builder.setIcon(R.drawable.ic_delete);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.delete(pro.getId());
                Intent intent = new Intent(getApplicationContext(), ProductDB.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}