package com.example.expensemanagement.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.databinding.ItemCategoryAmountBinding;
import com.example.expensemanagement.model.CategoryWithAmount;
import com.example.expensemanagement.utils.converter.MoneyConverter;

import java.util.ArrayList;
import java.util.List;

public class CategoryWithAmountAdapter extends RecyclerView.Adapter<CategoryWithAmountAdapter.CategoryWithAmountVH> {
    List<CategoryWithAmount> list = new ArrayList<>();

    public CategoryWithAmountAdapter(List<CategoryWithAmount> categoryWithAmounts) {
        this.list = categoryWithAmounts;
    }

    @NonNull
    @Override
    public CategoryWithAmountVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CategoryWithAmountVH(ItemCategoryAmountBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryWithAmountVH holder, int position) {
        CategoryWithAmount item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<CategoryWithAmount> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<CategoryWithAmount> newList) {
        this.list.clear();
        this.list = newList;
        notifyDataSetChanged();
    }
    public static class CategoryWithAmountVH extends RecyclerView.ViewHolder {
        ItemCategoryAmountBinding binding;

        public CategoryWithAmountVH(ItemCategoryAmountBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(CategoryWithAmount categoryWithAmount) {
            binding.imgCategoryIcon.setImageResource(categoryWithAmount.getCategoryImg());
            binding.tvCategoryName.setText(categoryWithAmount.getCategoryName());
            binding.tvAmount.setText(MoneyConverter.convertMoneyToString(categoryWithAmount.getSumAmount()));
        }
    }
}
