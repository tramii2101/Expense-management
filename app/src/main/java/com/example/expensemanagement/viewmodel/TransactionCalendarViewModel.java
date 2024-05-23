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
//    private LiveData<List<TransactionInformation>> transactionByMonth;
//    private LiveData<List<TransactionInformation>> transactionByDay;


//    public void setTransactionByMonth(Date date) {
//        transactionByMonth = repository.getTransactionByMonth(date);
//    }
//
//    public void setTransactionByDay(Date date) {
//        transactionByDay = repository.getTransactionByDay(date);
//    }

    public LiveData<List<Transaction>> getTransactionByMonth(Date date) {
        return repository.getTransactionByMonth(date);
    }

    public LiveData<List<Transaction>> getTransactionByDay(Date date) {
        return repository.getTransactionByDay(date);
    }

    public int deleteTransaction(Transaction transaction) {
        return repository.deleteTransaction(transaction);
    }
}
