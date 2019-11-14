package com.xixi.intelligent.bean

/**
 * Created by xixi on 2019/10/31.
 */
data class LoginBean(
    val access_token: String,
    val expires_in: Long,
    val refresh_token: String,
    val scope: String?,
    val token_type: String?,
    val error: String?,
    val error_description: String?
)