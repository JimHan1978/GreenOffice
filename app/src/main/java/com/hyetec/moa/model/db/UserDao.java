package com.hyetec.moa.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hyetec.moa.model.entity.UserEntity;
import java.util.List;

import io.reactivex.Flowable;


@Dao
public interface UserDao {


    /**
     * 插入
     * @param
     */
    @Insert
    void insertAll(List<UserEntity> userEntity);

    @Insert
    void insertUser(UserEntity userEntity);


    @Update
    void updateUser(UserEntity userEntity);

    /**
     * 查询
     * @param
     */
//    @Query("SELECT * FROM User ")
//    List<UserEntity> getUserAll();

    @Query("SELECT *,position.name AS positionName FROM User,position WHERE user.positionId=position.positionId AND  user.delFlag =:delFlag" )
    Flowable<List<UserEntity>>  getUserAll(boolean delFlag);

    @Query("SELECT * FROM User" )
    Flowable<List<UserEntity>>  getUserAll2();
    /**
     * 查询
     * @param
     */
    @Query("SELECT * FROM User WHERE userId =:id")
    UserEntity getUserById(int id);

    /**
     * 删除
     * @param
     */
    @Delete
    void delete(UserEntity  user);


}
