package com.xixi.intelligent.bean

import java.io.Serializable

/**
 * Created by xixi on 2019/10/31.
 */

data class TaskWXBean(
    val _entityName: String,
    val equEquipmentFault: EquEquipmentFault,
    val equFaultItem: EquFaultItem,
    val equipment: Equipment,
    val faultUU: String,
    val id: String,
    val status: String,
    val taskName: String,
    val version: Int
):Serializable

data class EquEquipmentFault(
    val _entityName: String,
    val faultDate: String,
    val faultName: String,
    val id: String,
    val version: Int
):Serializable

data class EquFaultItem(
    val _entityName: String,
    val faultItemName: String,
    val id: String,
    val version: Int
):Serializable