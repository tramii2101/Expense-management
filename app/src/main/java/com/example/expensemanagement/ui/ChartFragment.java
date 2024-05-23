package com.example.expensemanagement.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensemanagement.R;
import com.example.expensemanagement.databinding.FragmentChartBinding;
import com.example.expensemanagement.utils.Constant;
import com.example.expensemanagement.viewmodel.ChartViewModel;

import java.util.Calendar;
import java.util.Date;

public class ChartFragment extends Fragment {
    FragmentChartBinding binding;
    ChartViewModel viewModel;
    long totalIncome = 0;
    long totalExpense = 0;
    long balance = 0;
    long[] sum = new long[2];       // sum[0] = totalExpense; sum[1] = totalIncome
    String startDate;
    String endDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
    }

    void initView() {
        int[] color = {getResources().getColor(R.color.light_red, null), getResources().getColor(R.color.green, null)};
        binding.percentChart.setData(sum);
        binding.percentChart.setColors(color);

        binding.barChart.setColors(color);
        binding.barChart.setData(sum);

        binding.tvIncome.setText(String.valueOf(totalIncome));
        binding.tvExpense.setText(String.valueOf(totalExpense));
        binding.tvBalance.setText(String.valueOf(totalIncome - totalExpense));
        binding.tvFrom.setText(startDate);
        binding.tvTo.setText(endDate);
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
                        startDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        binding.tvFrom.setText(startDate);
                        getExpense(startDate, endDate);
                        getIncome(startDate, endDate);
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
                        endDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        binding.tvTo.setText(endDate);
                        getExpense(startDate, endDate);
                        getIncome(startDate, endDate);
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


    }

    void getExpense(String startDate, String endDate) {
        viewModel.getTotalExpenseBetweenDates(startDate, endDate).observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long expense) {
                if (expense != 0) {
                    totalExpense = expense;
                    sum[0] = totalExpense;
                    binding.percentChart.setData(sum);
                    binding.barChart.setData(sum);
                    balance = totalIncome - totalExpense;
                    binding.tvExpense.setText(String.valueOf(totalExpense));
                    binding.tvBalance.setText(String.valueOf(balance));
                }
            }
        });
    }

    void getIncome(String startDate, String endDate) {
        viewModel.getTotalIncomeBetweenDates(startDate, endDate).observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long income) {
                if (income != 0) {
                    totalIncome = income;
                    sum[1] = totalIncome;
                    binding.barChart.setData(sum);
                    binding.percentChart.setData(sum);
                    balance = totalIncome - totalExpense;
                    binding.tvIncome.setText(String.valueOf(totalIncome));
                    binding.tvBalance.setText(String.valueOf(balance));
                }
            }
        });
    }

}