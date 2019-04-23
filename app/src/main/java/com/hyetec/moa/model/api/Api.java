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
   // String APP_DOMAIN = "http://hyserver.hyetec.com:8180/";
    String IMG_URL = "http://hyserver.hyetec.com:8180/urm/";


   String APP_DOMAIN = "http://192.168.176.170:8180/";
//    String APP_DOMAIN = "http://192.168.10.10:8180/";

 //   String IMG_URL = "http://192.168.176.170:8882/urm/";

    //String APP_DOMAIN = "http://192.168.10.10:8180/";
    /**
     * 心知天气API密钥
     */
    String API_KEY = "sokppqeydnrkohxe";

    /**
     * 心知天气API图标
     */
    String API_WEATHER_ICON_URL = "https://s3.sencdn.com/web/icons/3d_50/%s.png";

    /**
     * Api Key
     */
    String API_KEY_KEY = "key";

    /**
     * API Language
     */
    String API_KEY_LANGUAGE = "language";

    /**
     * Api temperature unit
     */
    String API_KEY_TEMP_UNIT = "unit";

    /**
     * Api location
     */
    String API_KEY_LOCATION = "location";

    /**
     * userId Key
     */
    String API_USER_ID_KEY = "userId";
    String API_USER_PASSWORD_KEY = "password";
    String API_USER_NAME_KEY = "username";
}
