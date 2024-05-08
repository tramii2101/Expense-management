package com.example.expensemanagement.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.adapter.CategoryAdapter;
import com.example.expensemanagement.databinding.FragmentHomeBinding;
import com.example.expensemanagement.model.Category;
import com.example.expensemanagement.model.Transaction;
import com.example.expensemanagement.ui.custom.SpacesItemDecoration;
import com.example.expensemanagement.utils.Constant;
import com.example.expensemanagement.viewmodel.CategoryViewModel;
import com.example.expensemanagement.viewmodel.TransactionViewModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    static List<Category> listExpense = new ArrayList<>();
    static List<Category> listIncome = new ArrayList<>();
    //    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Category category;
    private FragmentHomeBinding binding;
    private DatePickerDialog datePickerDialog;
    private CategoryViewModel categoryViewModel;
    private TransactionViewModel transactionViewModel;
    String date;
    int thisSelected = -1;
    int lastSelected = -1;
    final CategoryAdapter categoryAdapter = new CategoryAdapter(listExpense);
    int categoryId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
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
        // init value for view model
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        binding.tvExpense.setSelected(true);// init first state is expense

        // set date is current date
        Date currentDate = new Date();
        date = Constant.FORMAT.format(currentDate);
        binding.tvDatetime.setText(date);

        // set adapter for recyclerview list category
        binding.rcvCategory.setAdapter(categoryAdapter);
        binding.rcvCategory.addItemDecoration(new SpacesItemDecoration(8, 3));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 2;
            }
        });
        binding.rcvCategory.setLayoutManager(gridLayoutManager);
    }

    @SuppressLint("NotifyDataSetChanged")
    void initData() {
        // init data for adapter
        if (!listExpense.isEmpty()) {
            categoryAdapter.setListCategory(listExpense);
            categoryAdapter.notifyDataSetChanged();
        } else {
            // get data from database
            categoryViewModel.getListExpense().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
                @Override
                public void onChanged(List<Category> categories) {
                    if (categories != null && !categories.isEmpty()) {
                        categoryAdapter.setListCategory(categories);
                        categoryAdapter.notifyDataSetChanged();
                        // Update listExpense with the new data
                        listExpense = categories;
                    }
                }
            });
        }
    }

    void handleEvent() {
        binding.tvExpense.setOnClickListener(v -> {
            // change color of btn income, btn expense
            v.setSelected(true);
            binding.tvIncome.setSelected(false);

            // update data of adapter
            if (!listExpense.isEmpty()) {
                categoryAdapter.updateListData(listExpense);
            } else {
                categoryViewModel.getListExpense().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        if (categories != null && !categories.isEmpty()) {
                            categoryAdapter.updateListData(categories);
                            listExpense = categories;
                        }
                    }
                });
            }
        });

        binding.tvIncome.setOnClickListener(v -> {
            v.setSelected(true);
            binding.tvExpense.setSelected(false);
            if (!listIncome.isEmpty()) {
                categoryAdapter.updateListData(listIncome);
            } else {
                categoryViewModel.getListIncome().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        if (categories != null && !categories.isEmpty()) {
                            categoryAdapter.updateListData(categories);
                            listIncome = categories;
                        }
                    }
                });
            }
        });

        // handle event when click item
        categoryAdapter.setOnClickItemListener((position, item) -> {
            categoryId = item.getCategoryId();
            category = item;
            lastSelected = thisSelected;
            thisSelected = position;

            RecyclerView.ViewHolder thisViewHolder = binding.rcvCategory.findViewHolderForAdapterPosition(thisSelected);
            if (thisViewHolder != null) {
                thisViewHolder.itemView.setSelected(true);
                categoryAdapter.notifyItemChanged(thisSelected);
            }

            if (thisSelected != lastSelected && lastSelected != RecyclerView.NO_POSITION) {
                RecyclerView.ViewHolder lastViewHolder = binding.rcvCategory.findViewHolderForAdapterPosition(lastSelected);
                if (lastViewHolder != null) {
                    lastViewHolder.itemView.setSelected(false);
                    categoryAdapter.notifyItemChanged(lastSelected);
                }
            }
        });

        // get date from date picker dialog
        binding.tvDatetime.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(this.getContext());
            datePickerDialog.show();
            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date = dayOfMonth + "-" + (month + 1) + "-" + year;
                    binding.tvDatetime.setText(date);
                }
            });
        });

        binding.btnOk.setOnClickListener(v -> {
            transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
            try {
                insertCategory();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void insertCategory() throws ParseException {
        Log.e("id", "insertCategory: " + categoryId);
        if (binding.edtNote.getText() == null) {
            binding.edtNote.setText("");
        } else {
            if (binding.edtAmount.getText() == null) {
                binding.edtAmount.setError("Bạn chưa nhập số tiền");
            } else {
                int amount = Integer.parseInt(binding.edtAmount.getText().toString());
                Transaction transaction = new Transaction(amount, Constant.FORMAT.parse(date), binding.edtNote.getText().toString(), category);
                if (transactionViewModel.addTransaction(transaction) >= 0) {
                    Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                    binding.edtNote.getText().clear();
                    binding.edtAmount.getText().clear();
                } else {
                    Toast.makeText(getContext(), "Update unsuccessfully! Please try later...", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

}