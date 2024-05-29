package com.example.expensemanagement.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.databinding.ItemTransactionBinding;
import com.example.expensemanagement.model.Transaction;
import com.example.expensemanagement.utils.Constant;
import com.example.expensemanagement.utils.converter.MoneyConverter;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<Transaction> listTransaction;
    private OnClickItemListener onClickListener;

    public TransactionAdapter() {
    }

    public TransactionAdapter(List<Transaction> listTransaction) {
        this.listTransaction = listTransaction;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new TransactionViewHolder(ItemTransactionBinding.inflate(layoutInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.bind(listTransaction.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onDoubleClick(position, listTransaction.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    public void setListTransaction(List<Transaction> listTransaction) {
        this.listTransaction = listTransaction;
    }

    public void setOnClickItemListener(OnClickItemListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        ItemTransactionBinding binding;

        public TransactionViewHolder(ItemTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Transaction item) {
            binding.tvCategoryName.setText(item.getCategory().getCategoryName());
            binding.tvTransactionDay.setText(Constant.FORMAT.format(item.getTransactionDate()));
            binding.imgCategoryIcon.setImageResource(item.getCategory().getCategoryImg());
            binding.tvAmount.setText(MoneyConverter.convertMoneyToString(item.getAmount()));
            if (item.getCategory().isIncome()) {
                binding.tvAmount.setTextColor(binding.getRoot().getResources().getColor(android.R.color.holo_green_light));
            } else {
                binding.tvAmount.setTextColor(binding.getRoot().getResources().getColor(android.R.color.holo_red_light));
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Transaction> newList) {
        listTransaction.clear();
        listTransaction = newList;
        notifyDataSetChanged();
    }

    public interface OnClickItemListener {
        void onDoubleClick(int position, Transaction item);
    }
}
