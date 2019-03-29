package com.hyetec.moa.model.api.service;

import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.BillEntity;
import com.hyetec.moa.model.entity.GroupEntity;
import com.hyetec.moa.model.entity.PositionEntity;
import com.hyetec.moa.model.entity.UserEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author : created by jimhan
 * 版本：1.0
 * 创建日期：2019/2/26
 * 描述： 通讯录服务器接口
 **/
public interface ContactsService {

    @POST("uap-urm/user/getInfo.json")
    Flowable<BaseResponse<UserEntity>> getUserInfo(@QueryMap Map<String, String> request);

    @POST("uap-urm/user/mlist.json")
    Flowable<BaseResponse<List<UserEntity>>> getUserList(@QueryMap Map<String, String> request);

    @POST("uap-urm/org/mlist.json")
    Flowable<BaseResponse<List<GroupEntity>>> getGroupList(@QueryMap Map<String, String> request);

    @POST("uap-urm/position/mlist.json")
    Flowable<BaseResponse<List<PositionEntity>>> getPositionList(@QueryMap Map<String, String> request);

    @POST("uap-urm/mobileLogin.json")
    Flowable<BaseResponse<UserEntity>> login(@QueryMap Map<String, String> request);

//    @POST("financePInfo/rest/selectFinancePInfo.json")
//    Flowable<BaseResponse<String>> monthBill(@QueryMap Map<String, String> request);

    @Headers("Content-Type: application/json")
    @POST("uap-urm/user/rest/getFinancePInfo.json")
    Flowable<BaseResponse<BillEntity>> monthBill(@Body Map<String, String> request);
}
