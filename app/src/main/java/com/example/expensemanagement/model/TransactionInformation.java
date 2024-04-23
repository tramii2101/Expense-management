package com.example.expensemanagement.model;

import androidx.room.ColumnInfo;

import java.util.Date;

public class TransactionInformation {
    @ColumnInfo(name = "transaction_name")
    private String transactionName;
    @ColumnInfo(name = "amount")
    private int amount;
    @ColumnInfo(name = "transaction_date")
    private Date transactionDate;
    @ColumnInfo(name = "note")
    private String note;
}
