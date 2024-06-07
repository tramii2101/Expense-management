package com.example.expensemanagement.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensemanagement.model.Category;
import com.example.expensemanagement.repository.CategoryRepository;

import java.util.List;

public class EditCategoryViewModel extends AndroidViewModel {
    CategoryRepository categoryRepository;

    private boolean income;

    LiveData<List<Category>> listExpenseCategory;
    LiveData<List<Category>> listIncomeCategory;

    public EditCategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public void insertCategory(Category category) {
        categoryRepository.insert(category);
    }

    public void getExpense() {
        listExpenseCategory = categoryRepository.getListExpense();
    }

    public void getIncome() {
        listIncomeCategory = categoryRepository.getListIncome();
    }

    public LiveData<List<Category>> getListExpenseCategory() {
        return listExpenseCategory;
    }

    public LiveData<List<Category>> getListIncomeCategory() {
        return listIncomeCategory;
    }
}
