package com.example.prm392_shopping_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.prm392_shopping_project.R;
import com.example.prm392_shopping_project.database.OrderDB;
import com.example.prm392_shopping_project.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    OrderDB db;
    TextView textViewFragmentOrderCount, textViewFragmentOrderAmount, textViewFragmentOrder;
    List<Order> list;

    public OrderFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new OrderDB(getContext());
        textViewFragmentOrderAmount = view.findViewById(R.id.textViewFragmentOrderRevenue);
        textViewFragmentOrder = view.findViewById(R.id.textViewFragmentOrder);
        textViewFragmentOrderCount = view.findViewById(R.id.textViewFragmentOrderCount);

        list = new ArrayList<>();
        list = db.getAll();

        textViewFragmentOrderCount.setText("Tổng đơn: " + list.size());
        long totalAmount = 0;
        String order = "";
        for (int i = 0; i < list.size(); i++) {
            totalAmount += list.get(i).getTotalBill();
            order += list.get(i);
        }
        textViewFragmentOrderAmount.setText("Doanh thu: " + totalAmount + " Đ");
        textViewFragmentOrder.setText(order);
    }
}
