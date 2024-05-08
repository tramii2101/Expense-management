package com.example.expensemanagement.room_db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.expensemanagement.R;
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
                            .addCallback(sRoomDatabaseCallback)
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
                CategoryDAO dao = INSTANCE.categoryDAO();
                dao.deleteAllCategory();

                dao.insertCategory(new Category("Ăn uống", R.drawable.ic_food, false));
                dao.insertCategory(new Category("Chi tiêu hằng ngày", R.drawable.ic_wallet, false));
                dao.insertCategory(new Category("Quần áo", R.drawable.ic_clothes, false));
                dao.insertCategory(new Category("Giao lưu", R.drawable.ic_party, false));
                dao.insertCategory(new Category("Y tế", R.drawable.ic_medical, false));
                dao.insertCategory(new Category("Mỹ phẩm", R.drawable.ic_cosmetic, false));
                dao.insertCategory(new Category("Giáo dục", R.drawable.ic_education, false));
                dao.insertCategory(new Category("Tiền điện", R.drawable.ic_electic, false));
                dao.insertCategory(new Category("Đi lại", R.drawable.ic_transport, false));
                dao.insertCategory(new Category("Liên lạc", R.drawable.ic_phone, false));
                dao.insertCategory(new Category("Tiền nhà", R.drawable.ic_house, false));
                dao.insertCategory(new Category("Tiền lương", R.drawable.ic_cash, true));
                dao.insertCategory(new Category("Tiền phụ cấp", R.drawable.ic_pig, true));
                dao.insertCategory(new Category("Tiền thưởng", R.drawable.ic_bonus, true));
                dao.insertCategory(new Category("Đầu tư", R.drawable.ic_income, true));
                dao.insertCategory(new Category("Thu nhập phụ", R.drawable.ic_money, true));

            });
        }
    };
}
