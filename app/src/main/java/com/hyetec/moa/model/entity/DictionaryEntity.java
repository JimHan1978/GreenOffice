package com.hyetec.moa.model.entity;

import java.io.Serializable;

public class DictionaryEntity implements Serializable {

    /**
     * del_flag : false
     * id : 1
     * label : 面授
     * sort : 1
     * type : tring_methods
     * value : 1
     */

    private boolean del_flag;
    private int id;
    private String label;
    private int sort;
    private String type;
    private String value;

    public boolean isDel_flag() {
        return del_flag;
    }

    public void setDel_flag(boolean del_flag) {
        this.del_flag = del_flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
