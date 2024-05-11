package com.example.expensemanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.expensemanagement.model.Transaction;

import java.util.List;

@Dao
public interface TransactionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertTransaction(Transaction transaction);

    @Query("DELETE FROM my_transaction")
    void deleteAllTransaction();

    @Query("SELECT * FROM my_transaction")
    LiveData<List<Transaction>> getAllTransaction();

    @Query("SELECT * FROM my_transaction INNER JOIN my_category ON my_transaction.category_id =my_category.category_id WHERE my_category.income = 1")
    LiveData<List<Transaction>> getAllExpense();

    @Query("SELECT * FROM my_transaction INNER JOIN my_category ON my_transaction.category_id = my_category.category_id WHERE my_category.income = 0")
    LiveData<List<Transaction>> getAllIncome();

    @Query("SELECT * FROM my_transaction WHERE strftime('%Y-%m', :date) = strftime('%Y-%m', :date) GROUP BY :date")
    LiveData<List<Transaction>> getTransactionByMonth(String date);

    @Query("SELECT SUM(amount) FROM my_transaction WHERE strftime('%Y-%m', :date) = strftime('%Y-%m', :date) AND income = 1")
    LiveData<Long> getTotalIncomeByMonth(String date);

    @Query("SELECT SUM(amount) FROM my_transaction WHERE strftime('%Y-%m', :date) = strftime('%Y-%m', :date) AND income = 1")
    LiveData<Long> getTotalExpenseByMonth(String date);

    @Query("SELECT * FROM my_transaction WHERE date(transaction_date) = :date")
    LiveData<List<Transaction>> getTransactionByDate(String date);

    @Query("SELECT SUM(amount) FROM my_transaction WHERE date(transaction_date) = :date")
    LiveData<Long> getIncomeByDate(String date);

    @Delete
    void deleteTransaction(Transaction transaction);

    @Query("SELECT * FROM my_transaction WHERE transaction_id = :id")
    LiveData<Transaction> getTransactionById(int id);
}