package com.hyetec.moa.model.api;

/**
 * @author xiaobailong24
 * @date 2017/7/25
 * 心知天气API
 */
public interface Api {
    /**
     * HTTP Url
     */
    String APP_DOMAIN = "http://hyserver.hyetec.com:8180/";
    String IMG_URL = "http://hyserver.hyetec.com:8180/urm/";
    String IMG_URL_ATTACHMENT = "http://hyserver.hyetec.com:8180/office/";

//     String IMG_URL_ATTACHMENT = "http://192.168.176.170:8180/";
//     String IMG_URL = "http://192.168.176.170:8180/";
//     String APP_DOMAIN = "http://192.168.176.170:8180/";



    /**
     * userId Key
     */
    String API_USER_ID_KEY = "userId";
    String API_USER_PASSWORD_KEY = "password";
    String API_USER_NAME_KEY = "username";
}
