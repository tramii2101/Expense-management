package com.example.expensemanagement.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expensemanagement.adapter.TransactionAdapter;
import com.example.expensemanagement.databinding.FragmentCalendarBinding;
import com.example.expensemanagement.model.TransactionInformation;
import com.example.expensemanagement.viewmodel.TransactionCalendarViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding binding;
    private TransactionCalendarViewModel viewModel;
    private TransactionAdapter adapter;
    private List<TransactionInformation> transactionList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TransactionCalendarViewModel.class);
        adapter = new TransactionAdapter(transactionList);
        if (transactionList == null) {
            viewModel.getTransactionByMonth(new Date()).observe(getViewLifecycleOwner(), transactionList -> {
                if (transactionList != null) {
                    adapter.setListTransaction(transactionList);
                    this.transactionList = transactionList;
                }
            });
        } else {
            adapter.setListTransaction(transactionList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        handleEvent();
    }

    void initView() {
        binding.listTransaction.setAdapter(adapter);
        binding.listTransaction.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    void initData() {

    }

    void handleEvent() {
        binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

        });


    }

}