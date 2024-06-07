package com.example.expensemanagement.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;
import com.example.expensemanagement.adapter.ListIconAdapter;
import com.example.expensemanagement.databinding.FragmentAddCategoryBinding;
import com.example.expensemanagement.model.Category;
import com.example.expensemanagement.ui.custom.SpacesItemDecoration;
import com.example.expensemanagement.viewmodel.EditCategoryViewModel;

import java.util.List;

public class AddCategoryFragment extends Fragment {
    private FragmentAddCategoryBinding binding;
    private ListIconAdapter adapter;
    private int thisSelected = -1;
    private int lastSelected = -1;
    private EditCategoryViewModel viewModel;
    private int icon;
    private boolean income;
    private String categoryName;

    static int[] listIcon = {R.drawable.ic_bear, R.drawable.ic_bin, R.drawable.ic_bonus, R.drawable.ic_cafe, R.drawable.ic_cash, R.drawable.ic_clothes, R.drawable.ic_cosmetic, R.drawable.ic_draw, R.drawable.ic_education, R.drawable.ic_education, R.drawable.ic_education, R.drawable.ic_electic, R.drawable.ic_food, R.drawable.ic_game, R.drawable.ic_game, R.drawable.ic_giftbox, R.drawable.ic_heartgift, R.drawable.ic_heartsetting, R.drawable.ic_house, R.drawable.ic_income, R.drawable.ic_list, R.drawable.ic_medical, R.drawable.ic_money, R.drawable.ic_paper, R.drawable.ic_party, R.drawable.ic_phone, R.drawable.ic_pig, R.drawable.ic_shoes, R.drawable.ic_sport, R.drawable.ic_transport, R.drawable.ic_wallet};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        handleEvent();
    }

    void initView() {
        adapter = new ListIconAdapter(listIcon);
        binding.rcvIcon.setAdapter(adapter);
        income = false;
        binding.rcvIcon.addItemDecoration(new SpacesItemDecoration(8, 4));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 8);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 2;
            }
        });
        binding.rcvIcon.setLayoutManager(gridLayoutManager);
        viewModel = new ViewModelProvider(this.getActivity()).get(EditCategoryViewModel.class);
    }

    void handleEvent() {
        adapter.setOnCLickItemListener((position, view) -> {
            lastSelected = thisSelected;
            thisSelected = position;

            RecyclerView.ViewHolder thisVH = binding.rcvIcon.findViewHolderForAdapterPosition(thisSelected);
            if (thisVH != null) {
                thisVH.itemView.setSelected(true);
                adapter.notifyItemChanged(thisSelected);
            }

            if (thisSelected != lastSelected && lastSelected != RecyclerView.NO_POSITION) {
                RecyclerView.ViewHolder lastVH = binding.rcvIcon.findViewHolderForAdapterPosition(lastSelected);
                if (lastVH != null) {
                    lastVH.itemView.setSelected(false);
                    adapter.notifyItemChanged(lastSelected);
                }
            }
        });

        binding.btnAdd.setOnClickListener(v -> {
            if (binding.edtCategoryName.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Chưa điền tên danh mục", Toast.LENGTH_SHORT).show();
            } else {
                categoryName = binding.edtCategoryName.getText().toString();
            }
            if (thisSelected == -1) {
                Toast.makeText(getContext(), "Chưa chọn icon", Toast.LENGTH_SHORT).show();
            } else {
                icon = listIcon[thisSelected];
            }
            if (thisSelected != -1 && !binding.edtCategoryName.getText().toString().isEmpty()) {
                viewModel.insertCategory(new Category(categoryName, icon, viewModel.isIncome()));
            }

            if (viewModel.isIncome()) {
                int pre_size = viewModel.getListIncomeCategory().getValue().size();
                viewModel.getListIncomeCategory().observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        if (categories.size() > pre_size) {
                            Toast.makeText(getContext(), "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    }
                });
            } else {
                int pre_size = viewModel.getListExpenseCategory().getValue().size();
                viewModel.getListExpenseCategory().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        if (categories.size() > pre_size) {
                            Toast.makeText(getContext(), "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    }
                });
            }
        });


        binding.ivBack.setOnClickListener(v-> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }


}