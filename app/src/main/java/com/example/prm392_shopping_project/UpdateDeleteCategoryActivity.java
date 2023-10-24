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

import com.example.prm392_shopping_project.database.CategoryDB;
import com.example.prm392_shopping_project.database.ProductDB;
import com.example.prm392_shopping_project.model.Category;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class UpdateDeleteCategoryActivity extends AppCompatActivity {
    TextView tv_id;
    EditText edt_name;
    ImageView imgView;
    Button btn_update, btn_dele, btn_uploadUpdate;
    CategoryDB db;
    ProductDB pdb;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_category);
        pdb = new ProductDB(this);
        db = new CategoryDB(this);
        tv_id = findViewById(R.id.tv_id);
        edt_name = findViewById(R.id.edt_nameupdate);
        imgView = findViewById(R.id.imgViewupdate);
        btn_update = findViewById(R.id.btn_update);
        btn_dele = findViewById(R.id.btn_dele);
        btn_uploadUpdate = findViewById(R.id.btn_uploadUpdate);

        Intent intent = getIntent();
        String name = (String) intent.getSerializableExtra("name");
        int id = (int) intent.getSerializableExtra("id");
        byte[] img = (byte[]) intent.getSerializableExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

        imgView.setImageBitmap(bitmap);
        tv_id.setText(id + "");
        edt_name.setText(name);

        btn_uploadUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(UpdateDeleteCategoryActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(tv_id.getText().toString());
                String name = edt_name.getText().toString();
                Category category = new Category(id, name, imageViewToByte(imgView));
                db.update(category);

            }
        });

        btn_dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(tv_id.getText().toString());
                if (pdb.getProductByCategoryId(id) == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Notification");
                    builder.setMessage("Are you sure remove this category?");
                    builder.setIcon(R.drawable.ic_delete);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.delete(id);

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(UpdateDeleteCategoryActivity.this, "This category still exists product!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Please grant this permission!", Toast.LENGTH_SHORT).show();
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
                imgView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}