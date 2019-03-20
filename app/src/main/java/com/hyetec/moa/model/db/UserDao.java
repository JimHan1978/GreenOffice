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

//    @Query("SELECT userId,userName,User.code,orgId,deptId,User.delFlag,email,User.positionId,orgName,photo,mobile," +
//            "position.name AS positionName,User.delFlag, shortName, pinyinName, initialIndex " +
//            "FROM User,position WHERE user.positionId=position.positionId AND  user.delFlag =:delFlag" )
//    Flowable<List<UserEntity>>  getUserAll(boolean delFlag);

    @Query("SELECT  userId,userName,User.qq,User.sortNo,User.deptName,User.code,User.orgId,deptId,User.delFlag,email,User.positionId,User.orgName,photo,mobile,User.delFlag," +
            " shortName, pinyinName, initialIndex,position.name AS positionName ,`Group`.name AS orgName  FROM User,position,`Group` " +
            "WHERE user.positionId=position.positionId AND user.orgId=`Group`.orgId  AND   user.delFlag =:delFlag" )
    Flowable<List<UserEntity>>  getUserAll(boolean delFlag);

    @Query("SELECT  userId,userName,User.qq,User.sortNo,User.deptName,User.code,User.orgId,deptId,User.delFlag,email,User.positionId,User.orgName,photo,mobile,User.delFlag," +
            " shortName, pinyinName, initialIndex,position.name AS positionName ,`Group`.name AS orgName  FROM User,position,`Group` " +
            "WHERE user.positionId=position.positionId AND user.orgId=`Group`.orgId  AND   user.delFlag =:delFlag ORDER BY sortNo" )
    Flowable<List<UserEntity>>  getUserSort(boolean delFlag);

//    @Query("SELECT * FROM User INNER JOIN position ON position.name = User.positionName  WHERE User.positionId=position.positionId AND  user.delFlag =:delFlag" )
//    Flowable<List<UserEntity>>  getUserAll(boolean delFlag);

//    @Query("SELECT * FROM User WHERE user.delFlag =:delFlag" )
//    Flowable<List<UserEntity>>  getUserAll(boolean delFlag);
    /**ANDANDANDAND
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
