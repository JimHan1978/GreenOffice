package com.hyetec.moa.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hyetec.moa.model.entity.MenuEntity;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author xiaobailong24
 * @date 2017/7/29
 * Room Database DAO
 * @see <a href="http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0726/8268.html">在Room中使用RxJava</a>
 */
@Dao
public interface MenuDao {


    /**
     * 插入
     *
     * @param menus 地址信息
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MenuEntity... menus);

    /**
     * 查询
     *
     * @return 所有地址列表
     */
    @Query("SELECT * FROM TraingMenu")
    Flowable<List<MenuEntity>> getAll();

    /**
     * 查询指定地址
     *
     * @param name 地址名称
     * @return 地址信息
     */
    @Query("SELECT * FROM TraingMenu WHERE name = :name")
    Flowable<List<MenuEntity>> getLocationByName(String name);

    /**
     * 更新地址信息
     *
     * @param menus 要更新的地址列表
     */
    @Update
    void updateAll(MenuEntity... menus);

    /**
     * 删除地址
     *
     * @param locations 要删除的地址列表
     */
    @Delete
    void deleteLocation(MenuEntity... locations);
}
