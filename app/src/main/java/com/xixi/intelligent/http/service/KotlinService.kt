package com.xixi.intelligent.http

import com.xixi.intelligent.bean.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Created by xixi on 2019/10/25.
 */
interface KotlinService {

//    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("app/rest/v2/oauth/token")
    fun login(@Header("Authorization") head: String, @QueryMap maps: Map<String, String>):Observable<LoginBean>

    //首页展示数据
    @GET("app/rest/v2/services/mes_TaskService/getAppTaskPage")
    fun getMainData(): Observable<BaseBean<MainBean>>

    //保养任务
    @GET("app/rest/v2/services/mes_MaintenanceService/getEquMaintenanceTasksToApp")
    fun getBYTask(@Query("currentPage") page: Int,@Query("pageSize") size: Int): Observable<BaseListBean<TaskBYBean>>

    //提交保养任务表单
    @POST("app/rest/v2/services/mes_MaintenanceService/setEquMaintenanceLogInApp")
    fun submitBYTask(@Body requestBody:RequestBody): Observable<BaseBean<Any?>>

    //维修任务
    @GET("app/rest/v2/services/mes_FaultService/getEquFaultTasksToApp")
    fun getWXTask(@Query("currentPage") page: Int,@Query("pageSize") size: Int): Observable<BaseListBean<TaskWXBean>>

    //提交维修任务表单
    @POST("app/rest/v2/services/mes_FaultService/setEquFaultLogInApp")
    fun submitWXTask(@Body requestBody:RequestBody): Observable<BaseBean<Any?>>

    //点检任务
    @GET("app/rest/v2/services/mes_CheckService/getEquCheckTasksToApp")
    fun getDJTask(@Query("currentPage") page: Int,@Query("pageSize") size: Int): Observable<BaseListBean<TaskDJBean>>

    //提交点检任务表单
    @POST("app/rest/v2/services/mes_CheckService/setEquCheckLogInApp")
    fun submitDJTask(@Body requestBody:RequestBody): Observable<BaseBean<Any?>>

    //通知消息
    @GET("app/rest/v2/services/mes_TaskService/getAllTask")
    fun getAllMsg(@Query("currentPage") page: Int,@Query("pageSize") size: Int): Observable<BaseBean<MsgBodyBean<MsgBean>>>

    //更改通知消息为已读状态
    @GET("app/rest/v2/services/mes_TaskService/updateMessageStatus")
    fun changeMsgRead(@Query("uuid") id: String?): Observable<BaseBean<Any?>>

    //设备报修获取设备名
    @GET("app/rest/v2/services/mes_EquipmentService/getEquipmentByNumApp")
    fun getEquipName(@Query("equipmentNum") id: String?): Observable<BaseBean<SBBXNameBean>>

    //设备报修获取所有不良项
    @GET("app/rest/v2/services/mes_EquipmentService/getAllFaultItem")
    fun getAllFaultItem(): Observable<BaseListBean<FaultItemBean>>

    //提交报修任务表单
    @POST("app/rest/v2/services/mes_EquipmentService/setEquipmentFaultInApp")
    fun submitSBBXTask(@Body requestBody:RequestBody): Observable<BaseBean<Any?>>

    //设备列表
    @GET("app/rest/v2/services/mes_EquipmentService/getAllEquipmentToApp")
    fun getEquipList(@Query("currentPage") page: Int,@Query("pageSize") size: Int): Observable<BaseListBean<EquipBean>>

}