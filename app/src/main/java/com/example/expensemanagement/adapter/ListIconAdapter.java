package com.example.expensemanagement.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.databinding.ItemIconBinding;

public class ListIconAdapter extends RecyclerView.Adapter<ListIconAdapter.ListIconViewHolder> {
    int[] icons;
    ListIconAdapter.OnClickItemListener listener;

    public ListIconAdapter(int[] icons) {
        this.icons = icons;
    }

    public void setOnCLickItemListener(OnClickItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListIconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ListIconViewHolder(ItemIconBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListIconViewHolder holder, int position) {
        int item = icons[position];
        holder.bind(item);
        holder.binding.getRoot().setOnClickListener(v -> {
            listener.onClick(position, item);
        });
    }

    @Override
    public int getItemCount() {
        return icons.length;
    }

    public static class ListIconViewHolder extends RecyclerView.ViewHolder {
        ItemIconBinding binding;

        public ListIconViewHolder(@NonNull ItemIconBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int icon) {
            binding.icon.setImageResource(icon);
        }
    }

    public interface OnClickItemListener {
        void onClick(int position, int item);
    }
}
