package com.example.expensemanagement.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensemanagement.model.Transaction;
import com.example.expensemanagement.repository.TransactionRepository;

import java.util.Date;
import java.util.List;

public class TransactionCalendarViewModel extends AndroidViewModel {
    private TransactionRepository repository;

    public TransactionCalendarViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
    }
    public LiveData<List<Transaction>> getTransactionByMonth(Date date) {
        return repository.getTransactionByMonth(date);
    }

    public LiveData<List<Transaction>> getTransactionByDay(String date) {
        return repository.getTransactionByDay(date);
    }

    public int deleteTransaction(Transaction transaction) {
        return repository.deleteTransaction(transaction);
    }

    public LiveData<List<Transaction>> getTransactionBetweenDates(String startDate, String endDate) {
        return repository.getTransactionBetweenDates(startDate, endDate);
    }
}
