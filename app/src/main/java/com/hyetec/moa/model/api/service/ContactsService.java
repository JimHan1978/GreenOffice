package com.hyetec.moa.model.api.service;

import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.GroupEntity;
import com.hyetec.moa.model.entity.UserEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author : created by jimhan
 * 版本：1.0
 * 创建日期：2019/2/26
 * 描述： 通讯录服务器接口
 **/
public interface ContactsService {

    @POST("urm/user/getInfo.json")
    Flowable<BaseResponse<UserEntity>> getUserInfo(@QueryMap Map<String, String> request);

    @POST("urm/user/mlist.json")
    Flowable<BaseResponse<List<UserEntity>>> getUserList(@QueryMap Map<String, String> request);

    @POST("urm/org/mlist.json")
    Flowable<BaseResponse<GroupEntity>> getOrgList(@QueryMap Map<String, String> request);
}
