package com.example.expensemanagement.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.expensemanagement.model.Transaction;
import com.example.expensemanagement.repository.TransactionRepository;

import java.util.Date;
import java.util.List;

public class TransactionCalendarViewModel extends ViewModel {
    private TransactionRepository repository;
//    private LiveData<List<TransactionInformation>> transactionByMonth;
//    private LiveData<List<TransactionInformation>> transactionByDay;

    public TransactionCalendarViewModel() {

    }
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
