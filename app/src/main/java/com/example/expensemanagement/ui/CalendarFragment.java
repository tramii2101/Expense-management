package com.example.expensemanagement.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
    private DialogTransactionDetailBinding detailBinding;
    private List<Transaction> transactionList = new ArrayList<>();
    private final TransactionAdapter adapter = new TransactionAdapter(transactionList);
    private String startDate;
    private String endDate;
    Calendar calendar = Calendar.getInstance();
    private Dialog detailDialog;
    private DatePicker datePicker;


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

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    void initView() {
        viewModel = new ViewModelProvider(this).get(TransactionCalendarViewModel.class);

        // Set text for tv select date
        calendar.setTime(new Date());
        binding.tvTo.setText(Constant.FORMAT.format(new Date()));
        calendar.add(Calendar.MONTH, 1);
        binding.tvFrom.setText(Constant.FORMAT.format(calendar.getTime()));

        // Set up recyclerview
        binding.rcvListTransaction.setAdapter(adapter);
        binding.rcvListTransaction.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setUpSwipeItem();

        // Set up dialog
        detailDialog = new Dialog(getContext());
        detailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT
        ));
        detailBinding = DialogTransactionDetailBinding.inflate(getLayoutInflater());
        detailDialog.setContentView(detailBinding.getRoot());
        detailDialog.setCancelable(true);
        detailDialog.setCanceledOnTouchOutside(true);
    }

    void initData() {
        Date currentDate = new Date();
        viewModel.getTransactionByMonth(currentDate).observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                if (transactions != null && !transactions.isEmpty()) {
                    adapter.updateData(transactions);
                    transactionList = transactions;
                } else {
                    adapter.updateData(transactionList);
                }
            }
        });
    }

    void handleEvent() {
        // get list transaction when change date
        binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            viewModel.getTransactionByDay(year + "-" + (month + 1) + "-" + dayOfMonth).observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
                @Override
                public void onChanged(List<Transaction> transactions) {
                    if (transactions != null && !transactions.isEmpty()) {
                        adapter.updateData(transactions);
                        transactionList = transactions;
                    } else {
                        adapter.updateData(transactionList);
                    }
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

        binding.tvFrom.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
            datePickerDialog.show();

            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    startDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    binding.tvFrom.setText(startDate);
                }
            });
        });

        binding.tvTo.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
            datePickerDialog.show();

            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    endDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    binding.tvTo.setText(endDate);
                }
            });
        });

        binding.tvFilter.setOnClickListener(v -> {
            viewModel.getTransactionBetweenDates(startDate, endDate).observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
                @Override
                public void onChanged(List<Transaction> transactions) {
                    if (transactions != null && !transactions.isEmpty()) {
                        adapter.updateData(transactions);
                        transactionList = transactions;
                    } else {
                        adapter.updateData(transactionList);
                    }
                }
            });
        });
    }

    /// Swipe to delete transaction in recyclerview
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
                        // If snack bar is dismissed by its, remove transaction from database
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