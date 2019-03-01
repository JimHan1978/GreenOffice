package com.hyetec.moa.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hyetec.moa.model.entity.PositionEntity;
import com.hyetec.moa.model.entity.UserEntity;

import java.util.List;


@Dao
public interface PositionDao {


    /**
     * 插入
     * @param
     */
    @Insert
    void insertAll(List<PositionEntity> positionEntities);

    @Insert
    void  insertPosition(PositionEntity positionEntities);


    /**
     * 查询
     * @param
     */
    @Query("SELECT * FROM position ")
    List<PositionEntity> getPositionAll();

    @Query("SELECT * FROM position WHERE positionId =:id")
    PositionEntity getPositionById(int id);

    /**
     * 修改
     * @param
     */
    @Update
    void updatePosition(PositionEntity positionEntity);

    /**
     * 删除
     * @param
     */
    @Delete
    void delete(PositionEntity positionEntity);


}
