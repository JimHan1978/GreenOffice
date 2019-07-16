package com.hyetec.moa.model.api.service;

import com.hyetec.moa.model.entity.ActivityDeleteEntity;
import com.hyetec.moa.model.entity.ActivityEventEntity;
import com.hyetec.moa.model.entity.ActivityLotteryEntity;
import com.hyetec.moa.model.entity.ActivitySignEntity;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.BillEntity;
import com.hyetec.moa.model.entity.BonusEntity;
import com.hyetec.moa.model.entity.BssidEntity;
import com.hyetec.moa.model.entity.DrawLotteryEntity;
import com.hyetec.moa.model.entity.GroupEntity;
import com.hyetec.moa.model.entity.LoginUserEntity;
import com.hyetec.moa.model.entity.MessageEntity;
import com.hyetec.moa.model.entity.PositionEntity;
import com.hyetec.moa.model.entity.PunchCardEntity;
import com.hyetec.moa.model.entity.ResultEntity;
import com.hyetec.moa.model.entity.TodayMoneyEntity;
import com.hyetec.moa.model.entity.UploadEntity;
import com.hyetec.moa.model.entity.UserEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
    Flowable<BaseResponse<List<GroupEntity>>> getGroupList(@QueryMap Map<String, String> request);

    @POST("urm/position/mlist.json")
    Flowable<BaseResponse<List<PositionEntity>>> getPositionList(@QueryMap Map<String, String> request);

    @POST("urm/mobileLogin.json")
    Flowable<BaseResponse<LoginUserEntity>> login(@QueryMap Map<String, String> request);

    @POST("office/attendance/getAttedaily.json")
    Flowable<BaseResponse<PunchCardEntity>> getAttendance(@QueryMap Map<String, String> request);

    @POST("office/attendance/submitWorkremark.json")
    Flowable<BaseResponse<PunchCardEntity>> submitWorkremark(@QueryMap Map<String, String> request);

    @POST("office/attendance/attesign.json")
    Flowable<BaseResponse<PunchCardEntity>> attesign(@QueryMap Map<String, String> request);

    @GET("office/lottery/loadLotterysNumber.json")
    Flowable<BaseResponse<List<BonusEntity>>> getLoadLotterysMonth(@QueryMap Map<String, String> request);

    @GET("office/attendance/getApBSSIds.json")
    Flowable<BaseResponse<BssidEntity>> getApBSSIds();

    @POST("office/lottery/drawLottery.json")
    Flowable<BaseResponse<DrawLotteryEntity>> getDrawLottery(@QueryMap Map<String, String> request);

    @POST("office/lottery/getActLotteryInfo.json")
    Flowable<BaseResponse<DrawLotteryEntity>> getDrawLotteryNum(@QueryMap Map<String, String> request);

    @GET("urm/mobileLogout.json")
    Flowable<BaseResponse<ResultEntity>> logout();

    @Headers("Content-Type: application/json")
    @POST("urm/user/rest/selectFinancePInfoContent.json")
    Flowable<BaseResponse<List<MessageEntity>>> getMessageLists(@Body Map<String, String> request);


    @POST("office/activityEvent/list.json")
    Flowable<BaseResponse<List<MessageEntity>>> getActivityEventLists();

    @POST("office/activitySign/save.json")
    Flowable<BaseResponse<ResultEntity>> activitySign(@QueryMap Map<String, String> request);

    @POST("office/activityEvent/detailsById.json")
    Flowable<BaseResponse<ActivityEventEntity>> getActivityEvent(@QueryMap Map<String, String> request);


    @POST("office/lottery/loadDailyLotterys.json")
    Flowable<BaseResponse<List<TodayMoneyEntity>>> loadDailyLotterys(@QueryMap Map<String, String> request);

    @POST("office/lottery/loadLotterysAct.json")
    Flowable<BaseResponse<List<ActivityLotteryEntity>>> loadActivityLottery(@QueryMap Map<String, String> request);

    @POST("office/activitySign/listByActId.json")
    Flowable<BaseResponse<List<ActivitySignEntity>>> loadActivitySign(@QueryMap Map<String, String> request);
// @POST("financePInfo/rest/selectFinancePInfo.json")
//  Flowable<BaseResponse<String>> monthBill(@QueryMap Map<String, String> request);

    @Headers("Content-Type: application/json")
    @POST("urm/user/rest/getFinancePInfo.json")
    Flowable<BaseResponse<BillEntity>> monthBill(@Body Map<String, String> request);

    @Multipart
    @POST("office/attachment/upload.json")
    Flowable<BaseResponse<List<UploadEntity>>> uploadImg(@Part List<MultipartBody.Part> partList);

    @POST("office/activityEvent/save.json")
    Flowable<BaseResponse<List<ResultEntity>>> deleteActivity(@QueryMap Map<String, String> request);
}
