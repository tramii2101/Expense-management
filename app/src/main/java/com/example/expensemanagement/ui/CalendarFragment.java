package com.example.expensemanagement.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.adapter.TransactionAdapter;
import com.example.expensemanagement.databinding.DialogTransactionDetailBinding;
import com.example.expensemanagement.databinding.FragmentCalendarBinding;
import com.example.expensemanagement.model.Transaction;
import com.example.expensemanagement.utils.Constant;
import com.example.expensemanagement.viewmodel.TransactionCalendarViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding binding;
    private TransactionCalendarViewModel viewModel;
    private TransactionAdapter adapter;
    private DialogTransactionDetailBinding detailBinding;
    private List<Transaction> transactionList = new ArrayList<>();
    int currentMonth;

    private Dialog detailDialog;

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
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
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
        binding.rcvListTransaction.setAdapter(adapter);
        binding.rcvListTransaction.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setUpSwipeItem();
        detailDialog = new Dialog(getContext());
        detailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT
        ));
        detailDialog.setContentView(detailBinding.getRoot());
        detailDialog.setCancelable(true);
        detailDialog.setCanceledOnTouchOutside(true);
    }

    void initData() {

    }

    void handleEvent() {
        // Get list transaction by day
        binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            viewModel.getTransactionByDay(new Date(dayOfMonth, month, year)).observe(getViewLifecycleOwner(), transactionList -> {
                if (transactionList != null) {
                    adapter.updateData(transactionList);
                    this.transactionList = transactionList;
                }
            });
        });

        // Handle when click transaction, show dialog include detail transaction
        adapter.setOnClickItemListener((position, item) -> {
            detailBinding.tvCategory.setText(item.getCategory().getCategoryName());
            detailBinding.tvAmount.setText(String.valueOf(item.getAmount()));
            detailBinding.tvDate.setText(Constant.FORMAT.format(item.getTransactionDate()));
            detailBinding.tvNote.setText(item.getNote());
            detailDialog.show();
        });
    }

    void setUpSwipeItem() {
        ItemTouchHelper.SimpleCallback touchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // implement swipe to delete transaction
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Transaction transaction = transactionList.get(position);
                transactionList.remove(position);
                adapter.notifyItemRemoved(position);
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Đã xóa 1 giao dịch", Snackbar.LENGTH_LONG);
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        // If snackbar is dismissed by its, remove transaction from database
                        if (event == DISMISS_EVENT_TIMEOUT) {
                            viewModel.deleteTransaction(transaction);
                        }
                    }
                });
                snackbar.setAction("Khôi phục", v -> {
                    transactionList.add(position, transaction);
                    adapter.notifyItemInserted(position);
                });
                snackbar.show();
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallBack);
        itemTouchHelper.attachToRecyclerView(binding.rcvListTransaction);
    }

}