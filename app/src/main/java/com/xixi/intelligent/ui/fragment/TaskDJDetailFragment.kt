package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import android.widget.RadioButton
import butterknife.OnClick
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.bean.BaseBean
import com.xixi.intelligent.bean.TaskDJBean
import com.xixi.intelligent.http.NetworkScheduler
import com.xixi.intelligent.http.ParamsUtil
import com.xixi.intelligent.http.subscriber.ApiObserver
import com.xixi.intelligent.utils.SPUtils
import com.xixi.intelligent.utils.TimeUtils
import kotlinx.android.synthetic.main.fragment_task_dj_detail.*
import kotlinx.android.synthetic.main.title_normal.*
import ocom.xixi.intelligent.http.ApiClient


/**
 * 点检任务表单详情
 */
class TaskDJDetailFragment : BaseSupportFragment() {

    var taskBean: TaskDJBean? = null
    var result = ""

    override fun getContentRes(): Int {
        return R.layout.fragment_task_dj_detail
    }

    companion object {
        fun newInstance(bundle: Bundle?): TaskDJDetailFragment {
            val fragment = TaskDJDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        page_title.text = "点检"
        initData()
    }

    fun initData(){
        taskBean = arguments!!.getSerializable("taskBean") as TaskDJBean
        if(taskBean!=null){
            byrw.text = taskBean?.equEquipmentCheck?.checkName?:""
            sbbh.text = taskBean?.equipment?.equipmentNum?:""
            sb_name.text = taskBean?.equipment?.equipmentName?:""
            start_time.text = TimeUtils.getNowString()
            tv_item_bl.text = taskBean?.equCheckItem?.checkItemName?:""
            user_name.text = SPUtils.getInstance().getString("userName","")
            if(taskBean?.equCheckItem?.unit!=null){
                item_unit.text = taskBean?.equCheckItem?.unit?:""
            }
            if(taskBean?.equCheckItem?.value!=null){
//                item_bl.text = taskBean?.equCheckItem?.value?:""
            }

        }
        select_type_group.setOnCheckedChangeListener { radioGroup, id ->
            result = radioGroup.findViewById<RadioButton>(id).tag.toString()
        }
        result = undone.tag.toString()
    }

    @OnClick(R.id.btn_submit)
    fun submit(){
        submitDJTask()
    }


    fun submitDJTask(){

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
    }
}
