package com.example.expensemanagement;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.expensemanagement.databinding.ActivityMainBinding;
import com.example.expensemanagement.ui.CalendarFragment;
import com.example.expensemanagement.ui.ChartFragment;
import com.example.expensemanagement.ui.HomeFragment;
import com.example.expensemanagement.utils.OnBottomNavVisibilityListener;

public class MainActivity extends AppCompatActivity implements OnBottomNavVisibilityListener {
    private ActivityMainBinding binding;

    private String thisFragment = "HomeFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setCurrentFragment(new HomeFragment());
        binding.nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_home) {
                if (!"HomeFragment".equals(thisFragment)) {
                    setCurrentFragment(new HomeFragment());
                }
            } else if (item.getItemId() == R.id.action_calendar) {
                if (!"CalendarFragment".equals(thisFragment)) {
                    setCurrentFragment(new CalendarFragment());
                    thisFragment = "CalendarFragment";
                }
            } else if (item.getItemId() == R.id.action_chart) {
                if (!"ChartFragment".equals(thisFragment)) {
                    setCurrentFragment(new ChartFragment());
                    thisFragment = "ChartFragment";
                }
            }

            return true;
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host, fragment).commit();
    }

    @Override
    public void onBottomNavVisibilityChanged(boolean show) {
        if (show) {
            binding.nav.setVisibility(View.VISIBLE);
        } else {
            binding.nav.setVisibility(View.GONE);
        }
    }
}