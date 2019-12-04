package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.bean.BaseBean
import com.xixi.intelligent.bean.TaskDJBean
import com.xixi.intelligent.http.NetworkScheduler
import com.xixi.intelligent.http.ParamsUtil
import com.xixi.intelligent.http.subscriber.ApiObserver
import kotlinx.android.synthetic.main.title_normal.*
import ocom.xixi.intelligent.http.ApiClient


/**
 * 设备详情
 */
class TaskSBLBDetailFragment : BaseSupportFragment() {

    var taskBean: TaskDJBean? = null
    var result = ""

    override fun getContentRes(): Int {
        return R.layout.fragment_task_sblb_detail
    }

    companion object {
        fun newInstance(bundle: Bundle?): TaskSBLBDetailFragment {
            val fragment = TaskSBLBDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        page_title.text = "设备详情"
        initData()
    }

    fun initData(){
        taskBean = arguments!!.getSerializable("taskBean") as TaskDJBean
        if(taskBean!=null){

        }
    }

//    @OnClick(R.id.btn_submit)
//    fun submit(){
//        submitDJTask()
//    }


    /*fun submitDJTask(){

        val params = ParamsUtil.init().TaskDJBody(taskBean?.id,start_time.text.toString(),item_bl.text.toString().trim(),result)

        ApiClient.instance.kotlinService.submitDJTask(params)
            .compose(NetworkScheduler.compose())
            .bindToLifecycle(this)
            .subscribe(object : ApiObserver<BaseBean<Any?>>() {
                override fun onSuccess(t: BaseBean<Any?>) {
                    if(t.isSuccess()){
                        toast("提交成功")
                        setFragmentResult(101,null)
                        pop()
                    }else{
                        toast(t.msg?:"")
                    }
                }

                override fun onError(msg: String?) {
                    toast(msg)
                }
            })
    }*/
}
