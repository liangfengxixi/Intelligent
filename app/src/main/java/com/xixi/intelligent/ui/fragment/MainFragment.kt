package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import butterknife.OnClick
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.bean.BaseBean
import com.xixi.intelligent.bean.MenuBean
import com.xixi.intelligent.bean.MsgBean
import com.xixi.intelligent.bean.MsgBodyBean
import com.xixi.intelligent.bean.event.MyMessageEvent
import com.xixi.intelligent.common.MData
import com.xixi.intelligent.http.NetworkScheduler
import com.xixi.intelligent.http.subscriber.ApiObserver
import com.xixi.intelligent.ui.activity.MainActivity
import com.xixi.intelligent.utils.SizeUtils
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_message.*
import ocom.xixi.intelligent.http.ApiClient
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import q.rorbin.badgeview.Badge
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView



class MainFragment:BaseSupportFragment() {

    var menus = arrayListOf<MenuBean>()
    val mFragments = arrayListOf<Fragment>(TaskRootFragment.newInstance(),PanelRootFragment.newInstance(),MessageRootFragment.newInstance(),
        PerformanceRootFragment.newInstance())

    override fun getContentRes(): Int {
        return R.layout.fragment_main
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    fun initView(){
        //EventBus注册
        EventBus.getDefault().register(this)
//        tablayout.setTabAdapter(MyTabAdapter())
        tablayout.setupWithFragment(childFragmentManager,R.id.fl_content,mFragments,MyTabAdapter())
        for(i in menus.indices){
            tablayout.getTabAt(i).titleView.setPadding(0,SizeUtils.dp2px(12f),0,0)
        }
    }
    fun initData(){
        getAllMessage()
    }

    //EventBus消息处理方法。
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MyMessageEvent) {
        when (event.type) {
            MData.Event_MSG_List -> {
                var num = event.passValue as Int
                tablayout.setTabBadge(2,num)
            }
            MData.Event_MSG_RQ -> {
                var num = tablayout.getTabAt(2).badgeView.badgeNumber+1
                tablayout.setTabBadge(2,num)
            }
        }
    }

    @OnClick(R.id.ll_photo,R.id.ll_loginout)
    fun btnClick(view : View){
        when (view.id) {
            R.id.ll_photo -> start(AccountFragment.newInstance())
            R.id.ll_loginout -> (_mActivity as MainActivity).loginOut()
        }
    }

    private inner class MyTabAdapter : TabAdapter {

        init {
            menus.add(MenuBean(R.drawable.menu_01_pressed, R.drawable.menu_01, "任务"))
            menus.add(MenuBean(R.drawable.menu_02_pressed, R.drawable.menu_02, "看板"))
            menus.add(MenuBean(R.drawable.menu_03_pressed, R.drawable.menu_03, "通知"))
            menus.add(MenuBean(R.drawable.menu_04_pressed, R.drawable.menu_04, "绩效"))
        }

        override fun getCount(): Int {
            return menus.size
        }

        override fun getIcon(position: Int): ITabView.TabIcon {
            val menu = menus[position]
            return ITabView.TabIcon.Builder()
                .setIcon(menu.mSelectIcon, menu.mNormalIcon)
                .setIconGravity(Gravity.TOP)
//                .setIconMargin(SizeUtils.dp2px(4f))
//                .setIconSize(SizeUtils.dp2px(26f), SizeUtils.dp2px(24f))
                .build()
        }

        override fun getBadge(position: Int): ITabView.TabBadge? {
            return ITabView.TabBadge.Builder().setBackgroundColor(ContextCompat.getColor(_mActivity,R.color.red)).setGravityOffset(18,6)
                .setOnDragStateChangedListener { dragState, badge, targetView ->

                }.build()
//            return ITabView.TabBadge.Builder().setBadgeGravity(Gravity.CENTER)/*.setBadgePadding(SizeUtils.dp2px(40f).toFloat())*/.build()
//            return null
        }

        override fun getTitle(position: Int): ITabView.TabTitle {
            val menu = menus[position]
            return ITabView.TabTitle.Builder()
                .setContent(menu.mTitle)
                .setTextColor(ContextCompat.getColor(context!!,R.color.white), ContextCompat.getColor(context!!,R.color.grayscale1))
                .build()
        }

        override fun getBackground(position: Int): Int {
            return -1

        }

    }

    fun getAllMessage(){
        ApiClient.instance.kotlinService.getAllMsg(1,20)
            .compose(NetworkScheduler.compose())
            .bindToLifecycle(this)
            .subscribe(object : ApiObserver<BaseBean<MsgBodyBean<MsgBean>>>() {
                override fun onSuccess(t: BaseBean<MsgBodyBean<MsgBean>>) {
                    if (t.isSuccess()) {
                        if(t.data!=null){
                            tablayout.setTabBadge(2,t.data.unReadCount)
                        }
                    }
                }

                override fun onError(msg: String?) {
                    toast(msg)
                }
            })
    }

    // 再点一次退出程序时间设置
    private val WAIT_TIME = 2000L
    private var TOUCH_TIME: Long = 0
    override fun onBackPressedSupport(): Boolean {
        if (_mActivity.getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop()
            return true
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                _mActivity.finish()
            } else {
                TOUCH_TIME = System.currentTimeMillis()
                toast(R.string.press_again_exit)
            }
            return true
        }
        return super.onBackPressedSupport()
    }

    override fun onDestroy() {
        super.onDestroy()
        //EventBus取消注册
        EventBus.getDefault().unregister(this)
    }
}