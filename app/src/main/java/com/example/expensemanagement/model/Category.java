package com.example.expensemanagement.model;

import java.util.ArrayList;

public class Category {
    private String categoryName;
    private String categoryId;
    private ArrayList<Money> listMoney;
    private boolean income;         //income: true, expense: false
    public Category() {
    }

    public Category(String categoryName, String categoryId, ArrayList<Money> listMoney, boolean income) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.listMoney = listMoney;
        this.income = income;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArrayList<Money> getListMoney() {
        return listMoney;
    }

    public void setListMoney(ArrayList<Money> listMoney) {
        this.listMoney = listMoney;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
