package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.bean.BaseListBean
import com.xixi.intelligent.bean.TaskDJBean
import com.xixi.intelligent.http.NetworkScheduler
import com.xixi.intelligent.http.subscriber.ApiObserver
import com.xixi.intelligent.ui.adapter.TaskDJAdapter
import com.xixi.intelligent.widget.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_task_dj.*
import kotlinx.android.synthetic.main.title_normal.*
import ocom.xixi.intelligent.http.ApiClient


/**
 * 点检任务
 */
class TaskDJFragment : BaseSupportFragment() {

    lateinit var mAdapter: TaskDJAdapter

    var mDataList: List<TaskDJBean> = arrayListOf()
    val pageSize = 10
    var pageNo = 1

    val RequestCode = 100
    val ResultCode = 101

    override fun getContentRes(): Int {
        return R.layout.fragment_task_dj
    }

    companion object {
        fun newInstance(): TaskDJFragment {
            return TaskDJFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        page_title.text = "点检任务"
        initRecyclerView()
    }

    //转场动画结束后加载数据，防止动画卡顿
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        getDJTask(true)
    }

    private fun initRecyclerView() {
        mAdapter = TaskDJAdapter(R.layout.item_task_dj, mDataList)
        mAdapter.openLoadAnimation()
        mRecyclerView.layoutManager = LinearLayoutManager(context)

        mRecyclerView.adapter = mAdapter
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mSwipeRefresh.setColorSchemeColors(context!!.resources.getColor(R.color.red))
        mSwipeRefresh.setOnRefreshListener { ->
            getDJTask(true)
        }
        mAdapter.setOnLoadMoreListener({
            getDJTask(false)
        }, mRecyclerView)
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->

        }

        mAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            val taskBean = adapter.getItem(position) as TaskDJBean
            when (view.id) {
                R.id.btn_by -> {
                    val bundle = Bundle()
                    bundle.putSerializable("taskBean", taskBean)
                    startForResult(TaskDJDetailFragment.newInstance(bundle),RequestCode)
                }
            }
        }

    }

    fun getDJTask(refresh:Boolean){
        if(refresh){
            pageNo = 1
            mAdapter.setEnableLoadMore(false)
        }else{
            pageNo++
        }

        ApiClient.instance.kotlinService.getDJTask(pageNo,pageSize)
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
            .subscribe(object : ApiObserver<BaseListBean<TaskDJBean>>() {
                override fun onSuccess(t: BaseListBean<TaskDJBean>) {
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

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
        if(requestCode == RequestCode && resultCode ==ResultCode){
            getDJTask(true)
        }
    }
}
