package com.example.expensemanagement.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.databinding.ItemCategoryHorizontalBinding;
import com.example.expensemanagement.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryHorizontalAdapter extends RecyclerView.Adapter<CategoryHorizontalAdapter.CategoryHorizontalViewHolder> {
    private List<Category> listCategory;

    public CategoryHorizontalAdapter() {
        this.listCategory = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListCategory(List<Category> listCategory) {
        this.listCategory = listCategory;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CategoryHorizontalViewHolder(ItemCategoryHorizontalBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHorizontalViewHolder holder, int position) {
        holder.bind(listCategory.get(position));
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    static class CategoryHorizontalViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryHorizontalBinding binding;

        public CategoryHorizontalViewHolder(ItemCategoryHorizontalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Category category) {
            binding.tvCategoryName.setText(category.getCategoryName());
            binding.ivCategory.setImageResource(category.getCategoryImg());
        }
    }
}
