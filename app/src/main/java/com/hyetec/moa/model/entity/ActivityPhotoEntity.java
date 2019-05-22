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
import java.util.List;


public class ActivityPhotoEntity implements Serializable {


    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    private  int  url;
}
