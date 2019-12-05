package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.bean.BaseListBean
import com.xixi.intelligent.bean.EquipBean
import com.xixi.intelligent.http.NetworkScheduler
import com.xixi.intelligent.http.subscriber.ApiObserver
import com.xixi.intelligent.ui.adapter.EquipAdapter
import com.xixi.intelligent.widget.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_task_sblb.*
import kotlinx.android.synthetic.main.title_normal.*
import ocom.xixi.intelligent.http.ApiClient


/**
 * 设备列表
 */
class TaskSBLBFragment : BaseSupportFragment() {

    lateinit var mAdapter: EquipAdapter

    var mDataList: List<EquipBean> = arrayListOf()
    val pageSize = 10
    var pageNo = 1

    override fun getContentRes(): Int {
        return R.layout.fragment_task_sblb
    }

    companion object {
        fun newInstance(): TaskSBLBFragment {
            return TaskSBLBFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        page_title.text = "设备列表"
        initRecyclerView()
    }

    //转场动画结束后加载数据，防止动画卡顿
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        getBYTask(true)
    }

    private fun initRecyclerView() {
        mAdapter = EquipAdapter(R.layout.item_task_sblb, mDataList)
        mAdapter.openLoadAnimation()
        mRecyclerView.layoutManager = LinearLayoutManager(context)

        mRecyclerView.adapter = mAdapter
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mSwipeRefresh.setColorSchemeColors(context!!.resources.getColor(R.color.red))
        mSwipeRefresh.setOnRefreshListener { ->
            getBYTask(true)
        }
        mAdapter.setOnLoadMoreListener({
            getBYTask(false)
        }, mRecyclerView)
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val taskBean = adapter.getItem(position) as EquipBean
            val bundle = Bundle()
            bundle.putSerializable("taskBean", taskBean)
            start(TaskSBLBDetailFragment.newInstance(bundle))
        }

    }

    fun getBYTask(refresh:Boolean){
        if(refresh){
            pageNo = 1
            mAdapter.setEnableLoadMore(false)
        }else{
            pageNo++
        }

        ApiClient.instance.kotlinService.getEquipList(pageNo,pageSize)
            .compose(NetworkScheduler.compose())
            .doOnSubscribe {
                if(refresh)
                    mSwipeRefresh.isRefreshing = true
            }
            .doFinally {
                mSwipeRefresh.isRefreshing = false
                mAdapter.emptyView = View.inflate(context, R.layout.layout_emptry, null)
            }
            .bindToLifecycle(this)
            .subscribe(object : ApiObserver<BaseListBean<EquipBean>>() {
                override fun onSuccess(t: BaseListBean<EquipBean>) {
                    if (t.isSuccess()) {
                        if(refresh){
                            mAdapter.setNewData(t.data)
                        }else{
                            mAdapter.addData(t.data)
                        }
                        if(t.data.size < pageSize){
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
}
