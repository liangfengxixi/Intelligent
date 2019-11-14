package com.xixi.intelligent.bean

import java.io.Serializable

/**
 * Created by xixi on 2019/11/13.
 */
data class MsgBean(
    val _entityName: String,
    val id: String,
    val read: Boolean,
    val receivedAt: String,
    val subject: String,
    val text: String,
    val version: Int
):Serializable
