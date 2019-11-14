package com.xixi.intelligent.bean

import java.io.Serializable

/**
 * Created by xixi on 2019/10/31.
 */

data class TaskBYBean(
    val _entityName: String,
    val equEquipmentMaintenance: EquEquipmentMaintenance,
    val equMaintenanceItem: EquMaintenanceItem,
    val equipment: Equipment,
    val id: String,
    val startTime: String,
    val status: String,
    val taskName: String,
    val timeLong: Int,
    val version: Int
):Serializable

data class EquEquipmentMaintenance(
    val _entityName: String,
    val equipmentMaintenanceName: String,
    val id: String,
    val version: Int
):Serializable

data class EquMaintenanceItem(
    val _entityName: String,
    val id: String,
    val maintenanceItemName: String,
    val remark: String?,
    val version: Int
):Serializable

data class Equipment(
    val _entityName: String,
    val equipmentName: String,
    val equipmentNum: String,
    val equipmentType: EquipmentType,
    val id: String,
    val version: Int
):Serializable

data class EquipmentType(
    val _entityName: String,
    val equipmentTypeName: String,
    val id: String,
    val version: Int
):Serializable