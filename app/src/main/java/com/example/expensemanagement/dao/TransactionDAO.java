package com.example.expensemanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.expensemanagement.model.Transaction;

import java.util.List;

@Dao
public interface TransactionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTransaction(Transaction transaction);

    @Query("DELETE FROM my_transaction")
    void deleteAllTransaction();

    @Query("SELECT * FROM my_transaction")
    LiveData<List<Transaction>> getAllTransaction();

//    @Query("SELECT * FROM my_transaction INNER JOIN my_category ON my_transaction.category_id =my_category.categoryId WHERE my_category.income = 1")
//    LiveData<List<Transaction>> getAllExpense();
//
//    @Query("SELECT * FROM my_transaction INNER JOIN my_category ON my_transaction.category_id = my_category.categoryId WHERE category.income = 0")
//    LiveData<List<Transaction>> getAllIncome();

}