package com.xixi.intelligent.bean

import java.io.Serializable

/**
 * Created by xixi on 2019/11/13.
 */
data class MainBean(
    val averTime: String,
    val curTime: String,
    val planTime: String,
    val sblb: String,
    val timeOutTask: String,
    val totalTask: String,
    val finishTask: String,
    val unStartTask: String,
    val djrw: List<Int>,
    val sbbx: List<Int>,
    val byrw: List<Int>,
    val wxrw: List<Int>,
    val zjrw: List<Int>
)
