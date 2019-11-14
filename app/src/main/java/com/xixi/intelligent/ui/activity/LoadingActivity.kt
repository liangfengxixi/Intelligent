package com.xixi.intelligent.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.tbruyelle.rxpermissions2.RxPermissions
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportActivity
import com.xixi.intelligent.common.ARConstant
import com.xixi.intelligent.utils.L
import com.xixi.intelligent.utils.SPUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

//启动加载页
class LoadingActivity: BaseSupportActivity() {
    override fun getContentRes(): Int {
        return R.layout.activity_loading
    }

    @SuppressLint("CheckResult")
    override fun initView(savedInstanceState: Bundle?) {
        val rxPermissions = RxPermissions(this)
        rxPermissions.requestEachCombined(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
//                Manifest.permission.RECORD_AUDIO
        ).subscribe { permission ->
            if (permission.granted) {
                // All permissions are granted !
                L.i("~~0~~"+permission)
            } else if (permission.shouldShowRequestPermissionRationale){
                L.i("~~1~~"+permission)
                // At least one denied permission without ask never again
            } else {
                L.i("~~2~~"+permission)
                // At least one denied permission with ask never again
                // Need to go to the settings
            }
            timer()
        }
    }

    @SuppressLint("CheckResult")
    private fun timer() {
        Observable.timer(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                intelLogic()
            }
    }

    fun intelLogic() {
//        ARouter.getInstance().build(ARConstant.AR_HomeAct).navigation()

        /*//引导页
        if (!SPUtils.getInstance().getBoolean("guide", false)*//*||isUpdate()*//*) {
            SPUtils.getInstance().put("guide", true)
            ARouter.getInstance().build(ARConstant.AR_GuideAct).navigation()
            finish()
            return;
        }*/

        if (!TextUtils.isEmpty(SPUtils.getInstance().getString("access_token", ""))) {
            ARouter.getInstance().build(ARConstant.AR_MainAct).navigation()
        } else {
            ARouter.getInstance().build(ARConstant.AR_LoginAct).navigation()
        }

        finish()
    }
}