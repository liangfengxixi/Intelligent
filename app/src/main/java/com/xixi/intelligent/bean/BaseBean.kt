package com.xixi.intelligent.bean

/**
 * Created by xixi on 2019/10/31.
 */
open class BaseBean<T>(val code: Int, val msg: String,val data: T) {
    fun isSuccess(): Boolean {
        return code == 1
    }
}

open class BaseListBean<T>(val code: Int, val msg: String,val data: List<T>) {
    fun isSuccess(): Boolean {
        return code == 1
    }
}

data class MsgBodyBean<T>(
    val unReadCount: Int,
    val message: List<T>
)