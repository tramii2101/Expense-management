package com.example.expensemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.expensemanagement.databinding.ActivityMainBinding;
import com.example.expensemanagement.databinding.FragmentHomeBinding;
import com.example.expensemanagement.ui.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host, new HomeFragment()).commit();

    }
}