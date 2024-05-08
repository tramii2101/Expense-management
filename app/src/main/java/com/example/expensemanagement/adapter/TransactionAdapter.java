package com.example.expensemanagement.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.databinding.ItemTransactionBinding;
import com.example.expensemanagement.model.TransactionInformation;
import com.example.expensemanagement.utils.converter.MoneyConverter;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter <TransactionAdapter.TransactionViewHolder> {
    private List<TransactionInformation> listTransaction;
    private OnDoubleClickListener onDoubleClickListener;

    public TransactionAdapter() {
    }

    public TransactionAdapter(List<TransactionInformation> listTransaction) {
        this.listTransaction = listTransaction;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new TransactionViewHolder(ItemTransactionBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.bind(listTransaction.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (onDoubleClickListener != null) {
                onDoubleClickListener.onDoubleClick(position, listTransaction.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    public void setListTransaction(List<TransactionInformation> listTransaction) {
        this.listTransaction = listTransaction;
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        ItemTransactionBinding binding;
        public TransactionViewHolder(ItemTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TransactionInformation item) {
            binding.tvCategoryName.setText(item.getCategory());
            binding.tvCategoryName.setCompoundDrawablesRelativeWithIntrinsicBounds(item.getIcon(), 0, 0, 0);
            binding.tvCategoryName.setCompoundDrawablePadding(2);
            binding.tvAmount.setText(MoneyConverter.convertMoneyToString(item.getAmount()));
            if (item.isIncome()) {
                binding.tvAmount.setTextColor(binding.getRoot().getResources().getColor(android.R.color.holo_green_light));
            } else {
                binding.tvAmount.setTextColor(binding.getRoot().getResources().getColor(android.R.color.holo_red_light));
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<TransactionInformation> newList) {
        listTransaction.clear();
        listTransaction = newList;
        notifyDataSetChanged();
    }

    public interface OnDoubleClickListener {
        void onDoubleClick(int position, TransactionInformation item);
    }
}
