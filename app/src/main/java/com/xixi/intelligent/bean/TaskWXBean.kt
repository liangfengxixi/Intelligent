package com.xixi.intelligent.bean

import java.io.Serializable

/**
 * Created by xixi on 2019/10/31.
 */

data class TaskWXBean(
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