package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.expensemanagement.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding = ActivityMainBinding.inflate(getLayoutInflater());
    }
}