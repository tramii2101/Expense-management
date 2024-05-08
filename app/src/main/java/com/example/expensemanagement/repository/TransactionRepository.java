package com.example.expensemanagement.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.expensemanagement.dao.TransactionDAO;
import com.example.expensemanagement.model.Transaction;
import com.example.expensemanagement.model.TransactionInformation;
import com.example.expensemanagement.room_db.TransactionRoomDatabase;
import com.example.expensemanagement.utils.Constant;

import java.util.Date;
import java.util.List;

public class TransactionRepository {
    private TransactionDAO transactionDAO;
    private LiveData<List<Transaction>> listTransaction;

    public TransactionRepository(Application application) {
        TransactionRoomDatabase db = TransactionRoomDatabase.getTransactionRoomDatabase(application);
        transactionDAO = db.transactionDAO();
        listTransaction = transactionDAO.getAllTransaction();
    }

    public LiveData<List<Transaction>> getListTransaction() {
        return listTransaction;
    }

    public long insert(Transaction transaction) {
        TransactionRoomDatabase.databaseWriteExecutor.execute(() -> {
            transactionDAO.insertTransaction(transaction);
        });
        return 0;
    }

    public void deleteAll() {
        TransactionRoomDatabase.databaseWriteExecutor.execute(() -> {
            transactionDAO.deleteAllTransaction();
        });
    }

    public LiveData<List<TransactionInformation>> getTransactionByDay(Date date) {
        return transactionDAO.getTransactionByDate(Constant.FORMAT.format(date));
    }

    public LiveData<List<TransactionInformation>> getTransactionByMonth(Date date) {
        return transactionDAO.getTransactionByMonth(Constant.FORMAT.format(date));
    }

    public LiveData<Long> getTotalIncomeByMonth(Date date) {
        return transactionDAO.getTotalIncomeByMonth(Constant.FORMAT.format(date));
    }

    public LiveData<Long> getTotalExpenseByMonth(Date date) {
        return transactionDAO.getTotalExpenseByMonth(Constant.FORMAT.format(date));
    }
}
