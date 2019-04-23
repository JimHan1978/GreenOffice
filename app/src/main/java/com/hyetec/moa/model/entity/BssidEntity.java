/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
package com.hyetec.moa.model.entity;



import java.io.Serializable;


public class BssidEntity implements Serializable {

    public String getApBssIds() {
        return apBssIds;
    }

    public void setApBssIds(String apBssIds) {
        this.apBssIds = apBssIds;
    }

    private  String apBssIds;

}
