package com.hyetec.moa.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hyetec.moa.model.entity.MenuEntity;
import com.hyetec.moa.model.entity.UserEntity;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author xiaobailong24
 * @date 2017/7/29
 * Room Database DAO
 * @see <a href="http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0726/8268.html">在Room中使用RxJava</a>
 */
@Dao
public interface UserDao {


    /**
     * 插入
     * @param
     */
    @Insert
    void insertAll(UserEntity ... users);

    /**
     * 查询
     * @param
     */
    @Query("SELECT * FROM User ")
    List<UserEntity> getAll();


    /**
     * 删除
     * @param
     */
    @Delete
    void delete(UserEntity  user);


}
