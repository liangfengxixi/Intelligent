package com.xixi.intelligent.base

import android.app.Application
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.xixi.intelligent.BuildConfig
import com.xixi.intelligent.utils.Utils
import me.yokeyword.fragmentation.Fragmentation

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this);
        Utils.init(this)
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
        // 栈视图等功能，建议在Application里初始化
        Fragmentation.builder()
            // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
            .stackViewMode(Fragmentation.BUBBLE)
            .debug(false)
            .install()
    }
}