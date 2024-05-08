package com.example.expensemanagement.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.expensemanagement.dao.CategoryDAO;
import com.example.expensemanagement.model.Category;
import com.example.expensemanagement.room_db.CategoryRoomDatabase;

import java.util.List;

public class CategoryRepository {
    private CategoryDAO categoryDAO;
//    private LiveData<List<Category>> listCategory;

    public CategoryRepository(Application application) {
        CategoryRoomDatabase db = CategoryRoomDatabase.getCategoryRoomDatabase(application);
        categoryDAO = db.categoryDAO();
    }

    public void insert(Category category) {
        CategoryRoomDatabase.databaseWriteExecutor.execute(() -> {
            categoryDAO.insertCategory(category);
        });
    }

    public LiveData<List<Category>> getListCategory() {
        return categoryDAO.getAllCategory();
    }

    public LiveData<List<Category>> getListExpense() {
        return categoryDAO.getExpenseCategory();
    }

    public LiveData<List<Category>> getListIncome() {
        return categoryDAO.getIncomeCategory();
    }

}
