package com.xixi.intelligent.http

import com.google.gson.Gson
import com.xixi.intelligent.utils.L
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject


/**
 * 接口请求参数
 * Created by xixi on 2019/4/30
 */

class ParamsUtil {

    val mediaType = MediaType.parse("application/json")

    companion object {

        private var paramsUtil: ParamsUtil? = null

        fun init(): ParamsUtil {
            if (paramsUtil == null) {
                paramsUtil = ParamsUtil()
            }
            return paramsUtil!!
        }
    }

    fun getParams(key: String, value: String): String {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(key, value)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val params = jsonObject.toString()
        L.i("params = $params")
        return params
    }


    /**
     * 更改用户信息
     *
     * @return
     */
    fun changeMyInfo(key: String, value: String): RequestBody {
        return RequestBody.create(mediaType, getParams(key, value))
    }

    /**
     * 保养任务
     *
     * @return
     */
    fun TaskBYBody(id: String?, startTime: String?, remark: String?, result: String): RequestBody {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("id", id)
            jsonObject.put("startTime", startTime)
            jsonObject.put("remark", remark)
            jsonObject.put("result", result)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val params = jsonObject.toString()
        L.i("params = $params")

        return RequestBody.create(mediaType, params)
    }

    /**
     * 点检任务
     *
     * @return
     */
    fun TaskDJBody(id: String?, startTime: String?, value: String?, result: String): RequestBody {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("id", id)
            jsonObject.put("startTime", startTime)
            jsonObject.put("remark", "")
            jsonObject.put("value", value)
            jsonObject.put("result", result)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val params = jsonObject.toString()
        L.i("params = $params")

        return RequestBody.create(mediaType, params)
    }

    /**
     * 维修任务
     *
     * @return
     */
    fun TaskWXBody(id: String?, startTime: String?, remark: String?, result: String): RequestBody {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("id", id)
            jsonObject.put("startTime", startTime)
            jsonObject.put("remark", remark)
            jsonObject.put("result", result)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val params = jsonObject.toString()
        L.i("params = $params")

        return RequestBody.create(mediaType, params)
    }

    /**
     * 设备报修
     *
     * @return
     */
    fun TaskSBBXBody(faultName: String?, equipmentId: String?, faultId: String?, remark: String): RequestBody {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("faultName", faultName)
            jsonObject.put("equipmentId", equipmentId)
            jsonObject.put("faultId", faultId)
            jsonObject.put("remark", remark)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val params = jsonObject.toString()
        L.i("params = $params")

        return RequestBody.create(mediaType, params)
    }

}
