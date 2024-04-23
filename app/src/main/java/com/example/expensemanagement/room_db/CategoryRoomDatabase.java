package com.example.expensemanagement.room_db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.expensemanagement.dao.CategoryDAO;
import com.example.expensemanagement.model.Category;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Category.class}, version = 1, exportSchema = false)
public abstract class CategoryRoomDatabase extends RoomDatabase {
    public abstract CategoryDAO categoryDAO();
    private static volatile CategoryRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CategoryRoomDatabase getCategoryRoomDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CategoryRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CategoryRoomDatabase.class, "my_category")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
