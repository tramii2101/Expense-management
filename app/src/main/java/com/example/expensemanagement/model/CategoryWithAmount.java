package com.example.expensemanagement.model;

import androidx.room.ColumnInfo;

public class CategoryWithAmount {
    @ColumnInfo(name = "category_id")
    private int categoryId;
    @ColumnInfo(name = "category_name")
    private String categoryName;
    @ColumnInfo(name = "category_img")
    private int categoryImg;
    @ColumnInfo(name = "sum_amount")
    private long sumAmount;

    public CategoryWithAmount() {
    }

    public CategoryWithAmount(int categoryId, String categoryName, int categoryImg, long sumAmount) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImg = categoryImg;
        this.sumAmount = sumAmount;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(int categoryImg) {
        this.categoryImg = categoryImg;
    }

    public long getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(long sumAmount) {
        this.sumAmount = sumAmount;
    }
}
