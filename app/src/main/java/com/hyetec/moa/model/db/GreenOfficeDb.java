package com.hyetec.moa.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hyetec.moa.model.entity.UserEntity;


/**
 * @author xiaobailong24
 * @date 2017/7/29
 * Room Database
 */
@Database(entities = UserEntity.class, version = 1)
public abstract class GreenOfficeDb extends RoomDatabase {
    public static final String DB_NAME = GreenOfficeDb.class.getSimpleName();

    /**
     * 获取数据库
     *
     * @return WeatherNowDao
     */
    public abstract UserDao getAll();
}
