package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.bean.BaseListBean
import com.xixi.intelligent.bean.TaskBYBean
import com.xixi.intelligent.http.NetworkScheduler
import com.xixi.intelligent.http.subscriber.ApiObserver
import com.xixi.intelligent.ui.adapter.TaskBYAdapter
import com.xixi.intelligent.widget.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_task_by.*
import kotlinx.android.synthetic.main.title_normal.*
import ocom.xixi.intelligent.http.ApiClient
import java.util.HashMap


/**
 * 保养任务
 */
class TaskBYFragment : BaseSupportFragment() {

    lateinit var mAdapter: TaskBYAdapter

    var mDataList: List<TaskBYBean> = arrayListOf()
    val pageSize = 10
    var pageNo = 1

    val RequestCode = 100
    val ResultCode = 101

    override fun getContentRes(): Int {
        return R.layout.fragment_task_by
    }

    companion object {
        fun newInstance(): TaskBYFragment {
            return TaskBYFragment()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        page_title.text = "保养任务"
        initRecyclerView()
        getBYTask(true)
    }

    private fun initRecyclerView() {
        mAdapter = TaskBYAdapter(R.layout.item_task_by, mDataList)
        mAdapter.openLoadAnimation()
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter.emptyView = View.inflate(context, R.layout.layout_emptry, null)
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

        }

        mAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            val taskBean = adapter.getItem(position) as TaskBYBean
            when (view.id) {
                R.id.btn_by -> {
                    val bundle = Bundle()
                    bundle.putSerializable("taskBean", taskBean)
                    startForResult(TaskBYDetailFragment.newInstance(bundle),RequestCode)
                }
            }
        }

    }

    fun getBYTask(refresh:Boolean){
        if(refresh){
            pageNo = 1
            mAdapter.setEnableLoadMore(false)
        }else{
            pageNo++
        }

        ApiClient.instance.kotlinService.getBYTask(pageNo,pageSize)
            .compose(NetworkScheduler.compose())
            .doOnSubscribe {
                if(refresh)
                    mSwipeRefresh.isRefreshing = true
            }
            .doFinally {
                mSwipeRefresh.isRefreshing = false
            }
            .bindToLifecycle(this)
            .subscribe(object : ApiObserver<BaseListBean<TaskBYBean>>() {
                override fun onSuccess(t: BaseListBean<TaskBYBean>) {
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
            getBYTask(true)
        }
    }
}
