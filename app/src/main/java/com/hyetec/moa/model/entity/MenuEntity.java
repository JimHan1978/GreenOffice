package com.hyetec.moa.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author xiaobailong24
 * @date 2017/8/15
 * 位置信息，存储数据库
 */
@Entity(tableName = "TraingMenu")
public class MenuEntity {
    /**
     * id : WX4FBXXFKE4F
     * name : 北京
     * country : CN
     * path : 北京,北京,中国
     * timezone : Asia/Shanghai
     * timezone_offset : +08:00
     */
    @NonNull
    @PrimaryKey
    private String id;
    private String name;

    public MenuEntity(@NonNull String id, String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return "Location: "
                + "\nid--->" + this.getId()
                + "\nname--->" + this.getName()
                + "\n";
    }
}

