package com.example.expensemanagement.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.expensemanagement.utils.converter.DateConverter;

import java.util.Date;

@TypeConverters({DateConverter.class})
@Entity(tableName = "my_transaction")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    private int transactionId;
    @ColumnInfo(name = "amount")
    private int amount;
    @ColumnInfo(name = "transaction_date")
    private Date transactionDate;
    @ColumnInfo(name = "note")
    private String note;
    @Embedded
    private Category category;

    public Transaction(int amount, Date transactionDate, String note, Category category) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.note = note;
        this.category = category;
    }

    public Transaction() {
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
