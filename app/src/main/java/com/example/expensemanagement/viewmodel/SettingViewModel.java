package com.example.expensemanagement.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.expensemanagement.model.Category;
import com.example.expensemanagement.repository.CategoryRepository;
import com.example.expensemanagement.repository.TransactionRepository;

import java.util.List;

public class SettingViewModel extends AndroidViewModel {
    private TransactionRepository transactionRepository;
    private CategoryRepository categoryRepository;
    LiveData<List<Category>> categories = new MutableLiveData<>();
    public SettingViewModel(@NonNull Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
        categoryRepository = new CategoryRepository(application);
    }

    public void deleteAllTransactions(){
        transactionRepository.deleteAll();
    }


}
