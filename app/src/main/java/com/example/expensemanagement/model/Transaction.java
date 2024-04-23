package com.example.expensemanagement.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.expensemanagement.utils.common.Converters;

import java.util.Date;

@TypeConverters({Converters.class})
@Entity(tableName = "my_transaction", foreignKeys = @ForeignKey(
        entity = Category.class,
        childColumns = "category_id",
        parentColumns = "categoryId"
))
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int transactionId;
    @ColumnInfo(name = "transaction_name")
    private String transactionName;
    @ColumnInfo(name = "amount")
    private int amount;
    @ColumnInfo(name = "transaction_date")
    private Date transactionDate;
    @ColumnInfo(name = "note")
    private String note;
    @ColumnInfo(name = "category_id")
    private int category_id;

    public Transaction() {
    }

    public Transaction(int transactionId, String transactionName, int amount, Date transactionDate, String note, int category_id) {
        this.transactionId = transactionId;
        this.transactionName = transactionName;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.note = note;
        this.category_id = category_id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
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

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
