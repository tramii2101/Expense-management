package com.example.expensemanagement.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.expensemanagement.dao.TransactionDAO;
import com.example.expensemanagement.model.CategoryWithAmount;
import com.example.expensemanagement.model.Transaction;
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

    public LiveData<List<Transaction>> getTransactionByDay(String date) {
        return transactionDAO.getTransactionByDate(Constant.FORMAT.format(date));
    }

    public LiveData<List<Transaction>> getTransactionByMonth(Date date) {
        return transactionDAO.getTransactionByMonth(Constant.FORMAT.format(date));
    }

    public LiveData<Long> getTotalIncomeByMonth(Date date) {
        return transactionDAO.getTotalIncomeByMonth(Constant.FORMAT.format(date));
    }

    public LiveData<Long> getTotalExpenseByMonth(Date date) {
        return transactionDAO.getTotalExpenseByMonth(Constant.FORMAT.format(date));
    }

    public int deleteTransaction(Transaction transaction) {
        TransactionRoomDatabase.databaseWriteExecutor.execute(() -> {
            transactionDAO.deleteTransaction(transaction);
        });
        return 0;
    }

    public LiveData<Transaction> getTransactionById(int id) {
        return transactionDAO.getTransactionById(id);
    }

    public LiveData<Long> getTotalIncomeBetweenDates(String startDate, String endDate) {
        return transactionDAO.getTotalIncomeBetweenDates(startDate, endDate);
    }

    public LiveData<Long> getTotalExpenseBetweenDates(String startDate, String endDate) {
        return transactionDAO.getTotalExpenseBetweenDates(startDate, endDate);
    }


    public LiveData<List<Transaction>> getIncomeBetweenDates(String startDate, String endDate) {
        return transactionDAO.getInComeBetweenDates(startDate, endDate);
    }

    public LiveData<List<Transaction>> getExpenseBetweenDates(String startDate, String endDate) {
        return transactionDAO.getExpenseBetweenDates(startDate, endDate);
    }

    public LiveData<List<Transaction>> getTransactionBetweenDates(String startDate, String endDate) {
        return transactionDAO.getTransactionsBetweenDates(startDate, endDate);
    }

    public LiveData<List<CategoryWithAmount>> getCategoriesWithAmountBetweenDates(String startDate, String endDate) {
        return transactionDAO.getCategoriesWithAmountBetweenDates(startDate, endDate);
    }
}
