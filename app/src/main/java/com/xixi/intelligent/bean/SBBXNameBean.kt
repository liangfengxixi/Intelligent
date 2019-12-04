package com.xixi.intelligent.bean

/**
 * Created by xixi on 2019/12/04.
 */
data class SBBXNameBean(
    val _entityName: String,
    val dataOfEntry: String,
    val equipmentName: String,
    val equipmentNum: String,
    val firstUsedTime: String,
    val id: String,
    val model: String,
    val remark: String,
    val status: String,
    val version: Int
)

data class FaultItemBean(
    val _entityName: String,
    val faultItemName: String,
    val id: String,
    val remark: String,
    val version: Int
)