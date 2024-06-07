package com.example.expensemanagement.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expensemanagement.adapter.CategoryHorizontalAdapter;
import com.example.expensemanagement.databinding.FragmentEditCategoryBinding;
import com.example.expensemanagement.model.Category;
import com.example.expensemanagement.utils.OnBottomNavVisibilityListener;
import com.example.expensemanagement.viewmodel.EditCategoryViewModel;

import java.util.List;


public class EditCategoryFragment extends Fragment {
    private FragmentEditCategoryBinding binding;
    private CategoryHorizontalAdapter adapter = new CategoryHorizontalAdapter();
    private EditCategoryViewModel viewModel;
    private OnBottomNavVisibilityListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnBottomNavVisibilityListener) {
            listener = (OnBottomNavVisibilityListener) context;
            listener.onBottomNavVisibilityChanged(false);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBottomNavVisibilityListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditCategoryBinding.inflate(inflater, container, false);
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
        binding.tvExpense.setSelected(true);
        binding.rcvCategory.setAdapter(adapter);
        binding.rcvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    void initData() {
        viewModel = new ViewModelProvider(this.getActivity()).get(EditCategoryViewModel.class);
        viewModel.getExpense();
        viewModel.getListExpenseCategory().observe(getViewLifecycleOwner(), expenseCategories -> {
            if (expenseCategories != null) {
                adapter.setListCategory(expenseCategories);
            }
        });
        viewModel.setIncome(false);
    }

    void handleEvent() {
        binding.tvExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvIncome.setSelected(false);
                binding.tvExpense.setSelected(true);
                viewModel.getExpense();
                viewModel.getListExpenseCategory().observe(getViewLifecycleOwner(), expenseCategories -> {
                    if (expenseCategories != null) {
                        adapter.setListCategory(expenseCategories);
                    }
                });
                viewModel.setIncome(false);
            }
        });

        binding.tvIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvExpense.setSelected(false);
                binding.tvIncome.setSelected(true);
                viewModel.getIncome();
                viewModel.getListIncomeCategory().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        if (categories != null) {
                            adapter.setListCategory(categories);
                        }
                    }
                });
                viewModel.setIncome(true);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.tvExpense.setSelected(true);
        binding.tvIncome.setSelected(false);
        viewModel.getExpense();
        viewModel.getListExpenseCategory().observe(getViewLifecycleOwner(), expenseCategories -> {
            if (expenseCategories != null) {
                adapter.setListCategory(expenseCategories);
            }
        });
        viewModel.setIncome(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener.onBottomNavVisibilityChanged(true);
    }
}