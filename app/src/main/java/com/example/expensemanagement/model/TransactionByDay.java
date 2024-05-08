//package com.example.expensemanagement.model;
//
//import androidx.room.ColumnInfo;
//import androidx.room.TypeConverters;
//
//import com.example.expensemanagement.utils.converter.ListConverter;
//
//import java.util.List;
//
//@TypeConverters({ListConverter.class})
//public class TransactionByDay {
//    @ColumnInfo(name = "transaction_date")
//    private String date;
//
//    private List<TransactionInformation> listTransaction;
//
//    public TransactionByDay(String date, List<TransactionInformation> listTransaction) {
//        this.date = date;
//        this.listTransaction = listTransaction;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public List<TransactionInformation> getListTransaction() {
//        return listTransaction;
//    }
//}
