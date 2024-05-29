package com.example.expensemanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.expensemanagement.model.CategoryWithAmount;
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

    @Query("SELECT * FROM my_transaction WHERE strftime('%Y-%m', transaction_date) = strftime('%Y-%m', :date)")
    LiveData<List<Transaction>> getTransactionByMonth(String date);

    @Query("SELECT SUM(amount) FROM my_transaction WHERE strftime('%Y-%m', transaction_date) = strftime('%Y-%m', :date) AND income = 1")
    LiveData<Long> getTotalIncomeByMonth(String date);

    @Query("SELECT SUM(amount) FROM my_transaction WHERE strftime('%Y-%m', transaction_date) = strftime('%Y-%m', :date) AND income = 0")
    LiveData<Long> getTotalExpenseByMonth(String date);

    @Query("SELECT * FROM my_transaction WHERE date(transaction_date) = :date")
    LiveData<List<Transaction>> getTransactionByDate(String date);

    @Query("SELECT SUM(amount) FROM my_transaction WHERE date(transaction_date) = :date")
    LiveData<Long> getIncomeByDate(String date);

    @Delete
    void deleteTransaction(Transaction transaction);

    @Query("SELECT * FROM my_transaction WHERE transaction_id = :id")
    LiveData<Transaction> getTransactionById(int id);

    @Query("SELECT SUM(amount) FROM my_transaction WHERE income = 1 AND (date(strftime('%Y-%m-%d', transaction_date)) BETWEEN :startDate AND :endDate)")
    LiveData<Long> getTotalIncomeBetweenDates(String startDate, String endDate);

    @Query("SELECT SUM(amount) FROM my_transaction WHERE income = 0 AND (date(strftime('%Y-%m-%d', transaction_date)) BETWEEN :startDate AND :endDate)")
    LiveData<Long> getTotalExpenseBetweenDates(String startDate, String endDate);

    @Query("SELECT * FROM my_transaction WHERE transaction_date BETWEEN :startDate AND :endDate AND category_id = :categoryId")
    LiveData<List<Transaction>> getTransactionsByCategoryBetweenDates(String startDate, String endDate, int categoryId);

    @Query("SELECT SUM(amount) FROM my_transaction WHERE (date(transaction_date) BETWEEN date(:startDate) AND date(:endDate)) AND transaction_id = :categoryId")
    LiveData<Long> getTotalAmountByCategoryBetweenDates(String startDate, String endDate, String categoryId);

    @Query("SELECT * FROM my_transaction WHERE (date(transaction_date) BETWEEN date(:startDate) AND date(:endDate))")
    LiveData<List<Transaction>> getTransactionsBetweenDates(String startDate, String endDate);

    @Query("SELECT * FROM my_transaction WHERE (date(transaction_date) BETWEEN date(:startDate) AND date(:endDate)) AND (income = 1)")
    LiveData<List<Transaction>> getInComeBetweenDates(String startDate, String endDate);

    @Query("SELECT * FROM my_transaction WHERE (date(transaction_date) BETWEEN date(:startDate) AND date(:endDate)) AND income = 0")
    LiveData<List<Transaction>> getExpenseBetweenDates(String startDate, String endDate);

    @Query("SELECT category_id, category_name, category_img, SUM(amount) as sum_amount FROM my_transaction WHERE (date(transaction_date) BETWEEN date(:startDate) AND date(:endDate)) GROUP BY category_id")
    LiveData<List<CategoryWithAmount>> getCategoriesWithAmountBetweenDates(String startDate, String endDate);
}