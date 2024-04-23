package com.example.expensemanagement.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.expensemanagement.dao.TransactionDAO;
import com.example.expensemanagement.model.Transaction;
import com.example.expensemanagement.room_db.TransactionRoomDatabase;

import java.util.List;

class TransactionRepository {
    private TransactionDAO transactionDAO;
    private LiveData<List<Transaction>> listTransaction;


    TransactionRepository(Application application) {
        TransactionRoomDatabase db = TransactionRoomDatabase.getTransactionRoomDatabase(application);
        transactionDAO = db.transactionDAO();
        listTransaction = transactionDAO.getAllTransaction();
    }

    public LiveData<List<Transaction>> getListTransaction() {
        return listTransaction;
    }

    void insert(Transaction transaction) {
        TransactionRoomDatabase.databaseWriteExecutor.execute(() -> {
            transactionDAO.insertTransaction(transaction);
        });
    }

    void deleteAll() {
        TransactionRoomDatabase.databaseWriteExecutor.execute(() -> {
            transactionDAO.deleteAllTransaction();
        });
    }
}
