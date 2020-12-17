package com.android.renqingjizhang.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.xiaoke.qqim.db.dao.MessageDao;
import com.xiaoke.qqim.db.entity.MessageBean;


@Database(entities = {MessageBean.class}, version = 1, exportSchema = false)
public abstract class QQIMDataBase extends RoomDatabase {
    private static QQIMDataBase INSTANCE;

    public static synchronized QQIMDataBase getDatabase(Context context) {
        if (INSTANCE == null) {

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), QQIMDataBase.class, "qqim.db")
                    .fallbackToDestructiveMigration()//数据升级时候删除所有数据；建议用添加migration的方式
                    //.addMigrations(migration)
                    // 允许主线程执行DB操作，一般不推荐
                    .allowMainThreadQueries()
                    //.openHelperFactory(factory)
                    .build();
        }
        return INSTANCE;
    }

    public static void setINSTANCE(QQIMDataBase INSTANCE) {
        QQIMDataBase.INSTANCE = INSTANCE;
    }

    public abstract MessageDao getMessageDao();



}
