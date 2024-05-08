package com.example.expensemanagement.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.expensemanagement.model.Category;
import com.example.expensemanagement.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> allCategory = new MutableLiveData<>();
    private LiveData<List<Category>> listExpense = new MutableLiveData<>();
    private LiveData<List<Category>> listIncome = new MutableLiveData<>();


    public CategoryViewModel(Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
        listExpense = categoryRepository.getListExpense();
        listIncome = categoryRepository.getListIncome();
        allCategory = categoryRepository.getListCategory();
    }


    public LiveData<List<Category>> getListExpense() {
        return categoryRepository.getListExpense();
    }

    public LiveData<List<Category>> getListIncome() {
        return categoryRepository.getListIncome();
    }

    public LiveData<List<Category>> getAllCategory() {
        return allCategory;
    }

    public void insert(Category category) {
        categoryRepository.insert(category);
    }
}
