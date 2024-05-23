package com.example.expensemanagement.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensemanagement.model.Transaction;
import com.example.expensemanagement.repository.TransactionRepository;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionRepository repository;
    private LiveData<List<Transaction>> allTransaction;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository(application);
        allTransaction = repository.getListTransaction();
    }

    public long addTransaction(Transaction transaction) {
        return repository.insert(transaction);
    }

    public LiveData<List<Transaction>> getAllTransaction() {
        return allTransaction;
    }
}
