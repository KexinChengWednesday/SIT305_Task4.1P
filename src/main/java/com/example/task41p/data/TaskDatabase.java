// 文件：TaskDatabase.java
package com.example.task41p.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {
    private static TaskDatabase instance;

    public abstract TaskDao taskDao();

    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // 仅用于简单项目测试
                    .build();
        }
        return instance;
    }
}