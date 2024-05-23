package com.example.expensemanagement.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensemanagement.repository.TransactionRepository;

public class ChartViewModel extends AndroidViewModel {
    private TransactionRepository transactionRepository;

    LiveData<Long> totalExpense;
    LiveData<Long> totalIncome;

    public ChartViewModel(@NonNull Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
    }

    public LiveData<Long> getTotalExpenseBetweenDates(String startDate, String endDate) {
        return transactionRepository.getTotalExpenseBetweenDates(startDate, endDate);
    }

    public LiveData<Long> getTotalIncomeBetweenDates(String startDate, String endDate) {
        return transactionRepository.getTotalIncomeBetweenDates(startDate, endDate);
    }

}
