package com.example.expensemanagement.model;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.expensemanagement.utils.converter.DateConverter;

import java.util.Date;
@TypeConverters({DateConverter.class})
public class TransactionInformation {
    @ColumnInfo(name = "category_img")
    private int icon;
    @ColumnInfo(name = "category_name")
    String category;
    @ColumnInfo(name = "amount")
    private int amount;
    @ColumnInfo(name = "transaction_date")
    private Date transactionDate;
    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "income")
    private boolean income;

    public TransactionInformation(int icon, String category, int amount, Date transactionDate, String note, boolean income) {
        this.icon = icon;
        this.category = category;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.note = note;
        this.income = income;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public int getIcon() {
        return icon;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public String getNote() {
        return note;
    }

    public boolean isIncome() {
        return income;
    }
}
