package com.xixi.intelligent.bean

import java.io.Serializable

/**
 * Created by xixi on 2019/11/12.
 */
data class TaskDJBean(
    val _entityName: String,
    val equCheckItem: EquCheckItem,
    val equEquipmentCheck: EquEquipmentCheck,
    val equipment: Equipment,
    val id: String,
    val startTime: String,
    val status: String,
    val taskName: String,
    val timeLong: Int,
    val version: Int
):Serializable

data class EquCheckItem(
    val _entityName: String,
    val checkItemName: String,
    val id: String,
    val typeEnum: String?,
    val unit: String?,
    val value: String?,
    val valueType: String?,
    val version: Int
):Serializable

data class EquEquipmentCheck(
    val _entityName: String,
    val checkName: String,
    val id: String,
    val version: Int
):Serializable
