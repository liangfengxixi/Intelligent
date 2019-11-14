package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.bean.*
import com.xixi.intelligent.bean.event.MyMessageEvent
import com.xixi.intelligent.common.MData
import com.xixi.intelligent.http.NetworkScheduler
import com.xixi.intelligent.http.subscriber.ApiObserver
import com.xixi.intelligent.ui.adapter.MsgAdapter
import com.xixi.intelligent.ui.adapter.TaskBYAdapter
import com.xixi.intelligent.widget.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.title_topbar.*
import ocom.xixi.intelligent.http.ApiClient
import org.greenrobot.eventbus.EventBus


/**
 * 消息
 */
class MessageFragment : BaseSupportFragment() {

    lateinit var mAdapter: MsgAdapter

    var mDataList = arrayListOf<MsgBean>()
    val pageSize = 20
    var pageNo = 1

    val RequestCode = 100
    val ResultCode = 101

    override fun getContentRes(): Int {
        return R.layout.fragment_message
    }

    companion object {
        fun newInstance(): MessageFragment {
            return MessageFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        page_title.text = "消息通知"
        initRecyclerView()
    }

//    override fun onLazyInitView(savedInstanceState: Bundle?) {
//        super.onLazyInitView(savedInstanceState)
//
//    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        getAllMsg(true)
    }

    private fun initRecyclerView() {
        mAdapter = MsgAdapter(R.layout.item_message, mDataList)
        mAdapter.openLoadAnimation()
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter.emptyView = View.inflate(context, R.layout.layout_emptry, null)
        mRecyclerView.adapter = mAdapter
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mSwipeRefresh.setColorSchemeColors(context!!.resources.getColor(R.color.red))
        mSwipeRefresh.setOnRefreshListener { ->
            getAllMsg(true)
        }
        mAdapter.setOnLoadMoreListener({
            getAllMsg(false)
        }, mRecyclerView)
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val msgBean = adapter.getItem(position) as MsgBean
            val bundle = Bundle()
            bundle.putSerializable("taskBean", msgBean)
            startForResult(MsgDetailFragment.newInstance(bundle),RequestCode)
        }
    }

    fun getAllMsg(refresh:Boolean){
        if(refresh){
            pageNo = 1
        }else{
            pageNo++
        }

        ApiClient.instance.kotlinService.getAllMsg(pageNo,pageSize)
            .compose(NetworkScheduler.compose())
            .doOnSubscribe {
                if(refresh)
                    mSwipeRefresh.isRefreshing = true
            }
            .doFinally {
                mSwipeRefresh.isRefreshing = false
            }
            .bindToLifecycle(this)
            .subscribe(object : ApiObserver<BaseBean<MsgBodyBean<MsgBean>>>() {
                override fun onSuccess(t: BaseBean<MsgBodyBean<MsgBean>>) {
                    if (t.isSuccess()) {
                        if(t.data!=null){
                            EventBus.getDefault().post(MyMessageEvent(MData.Event_MSG_List, t.data.unReadCount))
                        }
                        if(refresh){
                            mDataList.clear()
                        }
                        if(t.data!=null && !t.data.message.isEmpty()){
                            mDataList.addAll(t.data.message)
                        }
                        mAdapter.setNewData(mDataList)
                        if(t.data.message.size < pageSize){
                            mAdapter?.loadMoreEnd(false)
                        }else{
                            mAdapter?.loadMoreComplete()
                        }
                    }
                }

                override fun onError(msg: String?) {
                    toast(msg)
                }
            })
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
//        if(requestCode == RequestCode && resultCode ==ResultCode){
//            getAllMsg(true)
//        }
    }
}
