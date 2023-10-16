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

public class UDProductActivity extends AppCompatActivity {
    TextView tv_name, tv_description, tv_price, tv_unit, tv_quantity;
    ImageView imgView;
    ProductDB pdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udproduct);
        pdb = new ProductDB(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Product pro = (Product) bundle.get("object_product");
        tv_name = findViewById(R.id.tv_nameProduct);
        tv_description = findViewById(R.id.tv_descriptionPro);
        tv_price = findViewById(R.id.tv_pricePro);
        tv_unit = findViewById(R.id.tv_unitPro);
        tv_quantity = findViewById(R.id.tv_quantityPro);
        imgView = findViewById(R.id.img_avatar);

        tv_name.setText(pro.getName());
        tv_description.setText(pro.getDescription());
        tv_price.setText(String.valueOf(pro.getPrice()));
        tv_unit.setText(pro.getUnit());
        tv_quantity.setText(String.valueOf(pro.getQuantity()));
        byte[] img = (byte[]) pro.getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imgView.setImageBitmap(bitmap);
    }

    public void onUpdateProduct(View view) {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Product pro = (Product) bundle.get("object_product");
        Intent intent = new Intent(UDProductActivity.this, Update_Product.class);
        bundle.putSerializable("object_product", pro);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onDeleteProduct(View view) {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Product pro = (Product) bundle.get("object_product");
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Notification");
        builder.setMessage("Are you sure remove this product?");
        builder.setIcon(R.drawable.ic_delete);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pdb.delete(pro.getId());
                Intent intent = new Intent(getApplicationContext(), ProductDB.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}