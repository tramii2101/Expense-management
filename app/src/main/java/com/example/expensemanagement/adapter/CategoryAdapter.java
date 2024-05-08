package com.example.expensemanagement.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.databinding.ItemCategoryBinding;
import com.example.expensemanagement.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<Category> listCategory = new ArrayList<>();
    private OnClickItemListener onClickItemListener;

    public CategoryAdapter() {
    }

    public CategoryAdapter(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    public List<Category> getListCategory() {
        return listCategory;
    }

    public void setListCategory(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new CategoryViewHolder(ItemCategoryBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bindData(listCategory.get(position));
        Category item = listCategory.get(position);
        holder.itemView.setOnClickListener(v -> {
            onClickItemListener.onClick(position, item);
        });
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateListData(List<Category> newList) {
        listCategory.clear();
        listCategory = newList;
        notifyDataSetChanged();
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener {
        void onClick(int position, Category item);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;

        public CategoryViewHolder(ItemCategoryBinding b) {
            super(b.getRoot());
            binding = b;
        }


        void bindData(Category category) {
            binding.ivCategory.setImageResource(category.getCategoryImg());
            binding.tvCategoryName.setText(category.getCategoryName());
        }
    }
}
