package com.example.ktapp.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.ktapp.data.Person;
import com.example.ktapp.data.User;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.data.db
 * @ClassName: UserDatabase
 * @Description:这里使用@Database注解该类并添加了表名、数据库版本（每当我们改变数据库中的内容时它都会增加），所以这里使用exportSchema = false
 * @Author: LiuTao
 * @CreateDate: 2020/5/9 17:45
 * @UpdateUser: LiuTao
 */
@Database(entities = {User.class}, version = 3, exportSchema = true)
public abstract class UserDatabase extends RoomDatabase {
    private static final String DB_NAME = "UserDatabase.db";
    private static volatile UserDatabase instance;

    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static UserDatabase create(final Context context) {
        return Room.databaseBuilder(context, UserDatabase.class, DB_NAME).addMigrations(MIGRATION_1_2, MIGRATION_2_3).allowMainThreadQueries().build();
    }

    public abstract UserDao getUserDao();

    /**
     * ps
     * string: 类型对应TEXT
     * int：对应INTEGER
     * 升级到2
     * 1.创建一张符合我们要求的临时表temp_User
     * <p>
     * 2.将数据从旧表Student拷贝至临时表temp_User
     * <p>
     * 3.删除旧表User
     * <p>
     * 4.将临时表temp_User重命名为User
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE temp_User (" + "id INTEGER PRIMARY KEY NOT NULL," + "name TEXT," + "age TEXT)");
            database.execSQL("INSERT INTO temp_User (id, name, age) " + "SELECT id, name, age FROM User");
            database.execSQL("DROP TABLE User");
            database.execSQL("ALTER TABLE temp_User RENAME TO User");
        }
    };

    /**
     * 增加字段
     * 性别
     */
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE User" + " ADD COLUMN sex TEXT");
        }
    };


}
