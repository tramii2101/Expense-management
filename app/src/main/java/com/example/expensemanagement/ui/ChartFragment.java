package com.example.expensemanagement.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expensemanagement.R;
import com.example.expensemanagement.adapter.CategoryWithAmountAdapter;
import com.example.expensemanagement.databinding.FragmentChartBinding;
import com.example.expensemanagement.model.CategoryWithAmount;
import com.example.expensemanagement.utils.Constant;
import com.example.expensemanagement.utils.converter.MoneyConverter;
import com.example.expensemanagement.viewmodel.ChartViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChartFragment extends Fragment {
    FragmentChartBinding binding;
    ChartViewModel viewModel;
    long totalIncome = 0;
    long totalExpense = 0;
    long balance = 0;
    long[] sum = new long[2];       // sum[0] = totalExpense; sum[1] = totalIncome
    String startDate;
    String endDate;
    List<CategoryWithAmount> categoryWithAmount = new ArrayList<>();

    CategoryWithAmountAdapter adapter = new CategoryWithAmountAdapter(categoryWithAmount);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        handleEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        getExpense(startDate, endDate);
        getIncome(startDate, endDate);
        getTotalByCategory();
    }

    void initData() {
        sum[0] = totalIncome;
        sum[1] = totalExpense;
        viewModel = new ViewModelProvider(this).get(ChartViewModel.class);
        Date end = new Date();
        endDate = Constant.FORMAT.format(end);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date start = calendar.getTime();
        startDate = Constant.FORMAT.format(start);
        getExpense(startDate, endDate);
        getIncome(startDate, endDate);
        getTotalByCategory();
    }

    void initView() {
        int[] color = {getResources().getColor(R.color.light_red, null), getResources().getColor(R.color.green, null)};
        binding.percentChart.setData(sum);
        binding.percentChart.setColors(color);
        binding.tvIncome.setText(MoneyConverter.convertMoneyToString(totalIncome));
        binding.tvExpense.setText(MoneyConverter.convertMoneyToString(totalExpense));
        binding.tvBalance.setText(MoneyConverter.convertMoneyToString(totalIncome - totalExpense));
        binding.tvFrom.setText(startDate);
        binding.tvTo.setText(endDate);
        binding.rcvCategory.setAdapter(adapter);
        binding.rcvCategory.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
    }

    void handleEvent() {
        binding.tvFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.show();
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                        binding.tvFrom.setText(startDate);
                    }
                });
            }
        });

        binding.tvTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.show();
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                        binding.tvTo.setText(endDate);
                    }
                });
            }
        });

        binding.income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("FLAG", "1");
                bundle.putString("startDate", startDate);
                bundle.putString("endDate", startDate);
                ListTransactionFragment incomeFragment = new ListTransactionFragment();
                incomeFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host, incomeFragment).commit();
            }
        });

        binding.expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("FLAG", "0");
                bundle.putString("startDate", startDate);
                bundle.putString("endDate", startDate);
                ListTransactionFragment incomeFragment = new ListTransactionFragment();
                incomeFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host, incomeFragment).commit();
            }
        });

        binding.tvFilter.setOnClickListener(v -> {
            getExpense(startDate, endDate);
            getIncome(startDate, endDate);
            getTotalByCategory();
        });

    }

    void getExpense(String startDate, String endDate) {
        viewModel.getTotalExpenseBetweenDates(startDate, endDate).observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long expense) {
                if (expense != null && expense != 0) {
                    totalExpense = expense;
                    sum[0] = totalExpense;
                    binding.percentChart.setData(sum);
                    balance = totalIncome - totalExpense;
                    binding.tvExpense.setText(MoneyConverter.convertMoneyToString(totalExpense));
                    binding.tvBalance.setText(MoneyConverter.convertMoneyToString(balance));
                }
            }
        });
    }

    void getIncome(String startDate, String endDate) {
        viewModel.getTotalIncomeBetweenDates(startDate, endDate).observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long income) {
                if (income != null && income != 0) {
                    totalIncome = income;
                    sum[1] = totalIncome;
                    binding.percentChart.setData(sum);
                    balance = totalIncome - totalExpense;
                    binding.tvIncome.setText(MoneyConverter.convertMoneyToString(totalIncome));
                    binding.tvBalance.setText(MoneyConverter.convertMoneyToString(balance));
                }
            }
        });
    }

    void getTotalByCategory() {
        viewModel.getCategoriesWithAmountBetweenDates(startDate, endDate).observe(getViewLifecycleOwner(), new Observer<List<CategoryWithAmount>>() {
            @Override
            public void onChanged(List<CategoryWithAmount> categoryWithAmounts) {
                if (categoryWithAmounts != null && !categoryWithAmounts.isEmpty()) {
                    adapter.setList(categoryWithAmounts);
                    categoryWithAmount = categoryWithAmounts;
                }
            }
        });

    }

}