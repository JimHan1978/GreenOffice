package com.hyetec.moa.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hyetec.moa.model.entity.MenuEntity;

/**
 * @author xiaobailong24
 * @date 2017/7/29
 * Room Database
 */
@Database(entities = {MenuEntity.class}, version = 3)
public abstract class TraingDb extends RoomDatabase {
    public static final String DB_NAME = TraingDb.class.getSimpleName();

    /**
     * 获取数据库
     *
     * @return WeatherNowDao
     */
    public abstract MenuDao menuDao();
}
