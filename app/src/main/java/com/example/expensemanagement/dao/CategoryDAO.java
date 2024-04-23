package com.example.expensemanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.expensemanagement.model.Category;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(Category category);

    @Query("DELETE FROM my_category")
    void deleteAllCategory();

    @Query("SELECT * FROM my_category")
    LiveData<List<Category>> getAllCategory();

    @Query("SELECT * FROM my_category WHERE income = 1")
    LiveData<List<Category>> getIncomeCategory();

    @Query("SELECT * FROM my_category WHERE income = 0")
    LiveData<List<Category>> getExpenseCategory();
}
