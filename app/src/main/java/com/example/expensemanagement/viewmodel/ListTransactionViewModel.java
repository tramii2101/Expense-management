package com.example.expensemanagement.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensemanagement.model.Transaction;
import com.example.expensemanagement.repository.TransactionRepository;

import java.util.List;

public class ListTransactionViewModel extends AndroidViewModel {
    TransactionRepository transactionRepository;

    LiveData<List<Transaction>> expenseBetweenDates;
    LiveData<List<Transaction>> incomeBetweenDates;
    public ListTransactionViewModel(@NonNull Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
    }

    public LiveData<List<Transaction>> getExpensesBetweenDates(String startDate, String endDate) {
        expenseBetweenDates = transactionRepository.getExpenseBetweenDates(startDate, endDate);
        return expenseBetweenDates;
    }

    public LiveData<List<Transaction>> getIncomeBetweenDates(String startDate, String endDate) {
        incomeBetweenDates = transactionRepository.getIncomeBetweenDates(startDate, endDate);
        return incomeBetweenDates;
    }
}
