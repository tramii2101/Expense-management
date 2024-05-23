package com.example.expensemanagement.room_db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.expensemanagement.R;
import com.example.expensemanagement.dao.TransactionDAO;
import com.example.expensemanagement.model.Category;
import com.example.expensemanagement.model.Transaction;
import com.example.expensemanagement.utils.Constant;

import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Transaction.class, Category.class}, version = 1, exportSchema = false)
public abstract class TransactionRoomDatabase extends RoomDatabase {
    public abstract TransactionDAO transactionDAO();

    private static volatile TransactionRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TransactionRoomDatabase getTransactionRoomDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TransactionRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TransactionRoomDatabase.class, "my_transaction")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                TransactionDAO dao = INSTANCE.transactionDAO();
                dao.deleteAllTransaction();

                try {
                    dao.insertTransaction(new Transaction(100000, Constant.FORMAT.parse("30-04-2024"), "Rau, sườn", new Category(1, "Ăn uống", R.drawable.ic_food, false)));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    };
}
