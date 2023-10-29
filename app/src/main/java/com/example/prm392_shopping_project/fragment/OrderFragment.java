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
import com.example.prm392_shopping_project.database.CustomerDB;
import com.example.prm392_shopping_project.database.OrderDB;
import com.example.prm392_shopping_project.model.Customer;
import com.example.prm392_shopping_project.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    OrderDB orderDB;
    CustomerDB customerDB;
    TextView textViewFragmentOrderCount, textViewFragmentOrderAmount,
            textViewFragmentOrder, textViewFragmentOrderCustomer;
    List<Order> orderList;
    List<Customer> customerList;

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

        orderDB = new OrderDB(getContext());
        textViewFragmentOrderAmount = view.findViewById(R.id.textViewFragmentOrderRevenue);
        textViewFragmentOrder = view.findViewById(R.id.textViewFragmentOrder);
        textViewFragmentOrderCount = view.findViewById(R.id.textViewFragmentOrderCount);

        orderList = new ArrayList<>();
        orderList = orderDB.getAll();

//        customerList = new ArrayList<>();
//        customerList = customerDB.getAll();

        textViewFragmentOrderCount.setText("Tổng đơn: " + orderList.size());
        long totalAmount = 0;
        String order = "";
        for (int i = 0; i < orderList.size(); i++) {
            totalAmount += orderList.get(i).getTotalBill();
            order += orderList.get(i);
        }
//        String customer = "";
//        for (int y = 0; y < customerList.size(); y++) {
//            customer += customerList.get(y);
//        }
        textViewFragmentOrderAmount.setText("Doanh thu: " + totalAmount + " Đ");
        textViewFragmentOrder.setText(order);
//        textViewFragmentOrderCustomer.setText(customer);
    }
}
