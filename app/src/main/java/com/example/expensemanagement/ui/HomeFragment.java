package com.example.expensemanagement.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expensemanagement.R;
import com.example.expensemanagement.adapter.CategoryAdapter;
import com.example.expensemanagement.databinding.FragmentHomeBinding;
import com.example.expensemanagement.model.Category;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    static List<Category> listCategory = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private FragmentHomeBinding binding;
    private DatePickerDialog datePickerDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        binding.tvExpense.setSelected(true);        //init state is expense

        // set adapter for recyclerview list category
        CategoryAdapter categoryAdapter = new CategoryAdapter(listCategory);
        binding.rcvCategory.setAdapter(categoryAdapter);
        categoryAdapter.setOnClickItemListener((position, item) -> {

        });

        // set date is current date
        Date currentDate = new Date();
        binding.tvDatetime.setText(sdf.format(currentDate));

        binding.tvDatetime.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(this.getContext());
            datePickerDialog.show();
            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    binding.tvDatetime.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                }
            });
        });

        binding.tvIncome.setOnClickListener(v -> {
            v.setSelected(true);
            binding.tvExpense.setSelected(false);
        });

        binding.tvExpense.setOnClickListener(v -> {
            v.setSelected(true);
            binding.tvIncome.setSelected(false);
        });
    }

    void init() {
        listCategory.add(new Category(1, "Ăn uống", R.drawable.ic_food, false));
        listCategory.add(new Category(1, "Chi tiêu hằng ngày", R.drawable.ic_wallet, false));
        listCategory.add(new Category(1, "Quần áo", R.drawable.ic_clothes, false));
        listCategory.add(new Category(1, "Giao lưu", R.drawable.ic_party, false));
        listCategory.add(new Category(1, "Y tế", R.drawable.ic_medical, false));
        listCategory.add(new Category(1, "Mỹ phẩm", R.drawable.ic_cosmetic, false));
        listCategory.add(new Category(1, "Giáo dục", R.drawable.ic_education, false));
        listCategory.add(new Category(1, "Tiền điện", R.drawable.ic_electic, false));
        listCategory.add(new Category(1, "Đi lại", R.drawable.ic_transport, false));
        listCategory.add(new Category(1, "Liên lạc", R.drawable.ic_phone, false));
        listCategory.add(new Category(1, "Tiền nhà", R.drawable.ic_house, false));
        listCategory.add(new Category(1, "Tiền lương", R.drawable.ic_cash, true));
        listCategory.add(new Category(1, "Tiền phụ cấp", R.drawable.ic_pig, true));
        listCategory.add(new Category(1, "Tiền thưởng", R.drawable.ic_bonus, true));
        listCategory.add(new Category(1, "Đầu tư", R.drawable.ic_income, true));
        listCategory.add(new Category(1, "Thu nhập phụ", R.drawable.ic_money, true));
    }

}