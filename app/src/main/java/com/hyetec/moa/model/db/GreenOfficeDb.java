package com.hyetec.moa.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hyetec.moa.model.entity.GroupEntity;
import com.hyetec.moa.model.entity.PositionEntity;
import com.hyetec.moa.model.entity.UserEntity;


/**
 * @author zhaozhongnan
 * @date 2019/02/27
 * Room Database
 */
@Database(entities = {UserEntity.class,PositionEntity.class,GroupEntity.class} , version = 2)
public abstract class GreenOfficeDb extends RoomDatabase {

    public static final String DB_NAME = GreenOfficeDb.class.getSimpleName();

    public abstract UserDao userDao();

    public abstract PositionDao positionDao();

    public abstract GroupDao groupDao();

}
