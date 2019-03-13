package com.hyetec.moa.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hyetec.moa.model.entity.GroupEntity;
import com.hyetec.moa.model.entity.UserEntity;

import java.util.List;

@Dao
public interface GroupDao {


    /**
     * 插入
     * @param
     */
    @Insert
    void insertGroup(GroupEntity... groupEntities);

    @Insert
    void insertAll(List<GroupEntity> groupEntities);

    /**
     * 查询
     * @param
     */
    @Query("SELECT * FROM `Group` WHERE delFlag = 0 ")
    List<GroupEntity> getGroupAll();

    @Query("SELECT * FROM `Group` WHERE orgId = :id ")
    GroupEntity getGroupById(int id);

    /**
     * 修改
     * @param
     */
    @Update
    void updata(GroupEntity groupEntity);
    /**
     * 删除
     * @param
     */
    @Delete
    void delete(GroupEntity groupEntity);


}
