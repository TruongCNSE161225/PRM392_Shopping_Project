package com.example.prm392_shopping_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;


import com.example.prm392_shopping_project.adapter.AllCategoryAdapter;
import com.example.prm392_shopping_project.database.CategoryDB;
import com.example.prm392_shopping_project.model.Category;

import java.util.List;

public class AllCategoryActivity extends AppCompatActivity {

    RecyclerView RecyclerViewAllCategory;
    AllCategoryAdapter allCategoryAdapter;
    List<Category> allCategoryModelList;
    CategoryDB categoryDB;
    ImageView imageViewAllCategoryBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        RecyclerViewAllCategory = findViewById(R.id.recyclerViewAllCategory);
        imageViewAllCategoryBack = findViewById(R.id.imageViewAllCategoryBack);
        categoryDB = new CategoryDB(this);

        imageViewAllCategoryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(AllCategoryActivity.this, MainActivity.class);
                startActivity(back);
                finish();
            }
        });


        // adding data to model
        allCategoryModelList = categoryDB.getAll();
        /*allCategoryModelList.add(new AllCategoryModel(1, R.drawable.ic_fruits));
        allCategoryModelList.add(new AllCategoryModel(2, R.drawable.ic_veggies));
        allCategoryModelList.add(new AllCategoryModel(3, R.drawable.ic_meat));
        allCategoryModelList.add(new AllCategoryModel(4, R.drawable.ic_fish));
        allCategoryModelList.add(new AllCategoryModel(5, R.drawable.ic_spices));
        allCategoryModelList.add(new AllCategoryModel(6, R.drawable.ic_egg));
        allCategoryModelList.add(new AllCategoryModel(7, R.drawable.ic_drink));
        allCategoryModelList.add(new AllCategoryModel(8, R.drawable.ic_cookies));
        allCategoryModelList.add(new AllCategoryModel(8, R.drawable.ic_juce));*/


        setCategoryRecycler(allCategoryModelList);

    }

    private void setCategoryRecycler(List<Category> allcategoryModelList) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        RecyclerViewAllCategory.setLayoutManager(layoutManager);
        RecyclerViewAllCategory.addItemDecoration(new GridSpacingItemDecoration(4, dpToPx(16), true));
        RecyclerViewAllCategory.setItemAnimator(new DefaultItemAnimator());
        allCategoryAdapter = new AllCategoryAdapter(this, allcategoryModelList);
        RecyclerViewAllCategory.setAdapter(allCategoryAdapter);
    }

//    @Override
//    public void onItemClick(View view, int position) {
//            Category category = allCategoryAdapter.getCategoryAt(position);
//            Intent intent= new Intent(AllCategory.this, AllProductActivity.class);
//            Bundle bundle=new Bundle();
//            bundle.putSerializable("object_category", (Serializable) category);
//            intent.putExtras(bundle);
//            startActivity(intent);
//    }


    // now we need some item decoration class for manage spacing

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}


