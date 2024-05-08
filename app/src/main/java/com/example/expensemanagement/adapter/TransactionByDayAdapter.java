//package com.example.expensemanagement.adapter;
//
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.expensemanagement.databinding.ItemTransactionByDayBinding;
//import com.example.expensemanagement.model.TransactionByDay;
//
//import java.util.List;
//
//public class TransactionByDayAdapter extends RecyclerView.Adapter<TransactionByDayAdapter.TransactionByDayViewHolder> {
//    private List<TransactionByDay> list;
//
//    // Create a RecyclerViewPool to share views between multiple child RecyclerView
//    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
//
//    public TransactionByDayAdapter(List<TransactionByDay> list) {
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public TransactionByDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        return new TransactionByDayViewHolder(ItemTransactionByDayBinding.inflate(layoutInflater));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TransactionByDayViewHolder holder, int position) {
//        TransactionByDay transactionByDay = list.get(position);
////        holder.binding.tvDate.setText(Constant.FORMAT.format(transactionByDay.getDate()));
//        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.binding.rcvListTransactionByDate.getContext(), LinearLayoutManager.VERTICAL, false);
//
//        // Define how many items to prefetch when the child RecyclerView is nested inside the parent RecyclerView
//        layoutManager.setInitialPrefetchItemCount(transactionByDay.getListTransaction().size());
//
//        //Create an instance of the child  item view adapter and set its adapter, layout manager and RecyclerViewPool
//        TransactionAdapter transactionAdapter = new TransactionAdapter(transactionByDay.getListTransaction());
//        holder.binding.rcvListTransactionByDate.setLayoutManager(layoutManager);
//        holder.binding.rcvListTransactionByDate.setAdapter(transactionAdapter);
//        holder.binding.rcvListTransactionByDate.setRecycledViewPool(viewPool);
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public static class TransactionByDayViewHolder extends RecyclerView.ViewHolder {
//        ItemTransactionByDayBinding binding;
//
//        public TransactionByDayViewHolder(@NonNull ItemTransactionByDayBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//    }
//}
