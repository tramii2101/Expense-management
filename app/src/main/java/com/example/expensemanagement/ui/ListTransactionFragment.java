package com.example.expensemanagement.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expensemanagement.adapter.TransactionAdapter;
import com.example.expensemanagement.databinding.FragmentListTransactionBinding;
import com.example.expensemanagement.model.Transaction;
import com.example.expensemanagement.utils.OnBottomNavVisibilityListener;
import com.example.expensemanagement.viewmodel.ListTransactionViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListTransactionFragment extends Fragment {
    private FragmentListTransactionBinding binding;

    private ListTransactionViewModel viewModel;

    private List<Transaction> transactions = new ArrayList<>();

    private TransactionAdapter adapter = new TransactionAdapter(transactions);

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
        binding = FragmentListTransactionBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ListTransactionViewModel.class);

        assert getArguments() != null;
        String FLAG = getArguments().getString("FLAG");
        String startDate = getArguments().getString("START_DATE");
        String endDate = getArguments().getString("END_DATE");
        adapter = new TransactionAdapter();
        binding.rcvTransaction.setAdapter(adapter);
        binding.rcvTransaction.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        if (FLAG != null) {
            if (FLAG.equals("1")) {
                viewModel.getIncomeBetweenDates(startDate, endDate).observe(getViewLifecycleOwner(), transactions -> {
                    if (transactions != null) {
                        this.transactions = transactions;
                        adapter.updateData(transactions);
                    }
                });
            } else {
                viewModel.getExpensesBetweenDates(startDate, endDate).observe(getViewLifecycleOwner(), transactions -> {
                    if (transactions != null) {
                        this.transactions = transactions;
                        adapter.updateData(transactions);
                    }
                });
            }
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener.onBottomNavVisibilityChanged(true);
    }

}