package com.xixi.intelligent.bean

import java.io.Serializable

/**
 * Created by xixi on 2019/12/5.
 */

data class EquipBean(
    val _entityName: String,
    val dataOfEntry: String,
    val equipmentName: String,
    val equipmentNum: String,
    val equipmentType: EquipmentType,
    val firstUsedTime: String,
    val id: String,
    val manufacturer: Manufacturer,
    val model: String,
    val picture: Picture,
    val remark: String,
    val status: String,
    val users: List<User>,
    val version: Int,
    val workshop: Workshop
):Serializable

data class Manufacturer(
    val _entityName: String,
    val id: String,
    val suppllierName: String,
    val suppllierNo: String,
    val version: Int
):Serializable

data class Picture(
    val _entityName: String,
    val createDate: String,
    val extension: String,
    val id: String,
    val name: String,
    val version: Int
):Serializable

data class User(
    val _entityName: String,
    val id: String,
    val login: String,
    val name: String,
    val version: Int
):Serializable

data class Workshop(
    val _entityName: String,
    val id: String,
    val name: String,
    val version: Int
):Serializable