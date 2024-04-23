package com.example.expensemanagement.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_category")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int categoryId;

    @ColumnInfo(name = "category_name")
    private String categoryName;

    @ColumnInfo(name = "category_img")
    private int categoryImg;
    @ColumnInfo(name = "income")
    private boolean income;         //income: true, expense: false

    public Category() {
    }

    public Category(int categoryId, String categoryName, int categoryImg, boolean income) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImg = categoryImg;
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

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(int categoryImg) {
        this.categoryImg = categoryImg;
    }
}
