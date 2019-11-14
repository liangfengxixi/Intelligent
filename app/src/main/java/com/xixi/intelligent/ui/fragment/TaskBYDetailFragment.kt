package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import android.widget.RadioButton
import butterknife.OnClick
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.bean.BaseBean
import com.xixi.intelligent.bean.TaskBYBean
import com.xixi.intelligent.http.NetworkScheduler
import com.xixi.intelligent.http.ParamsUtil
import com.xixi.intelligent.http.subscriber.ApiObserver
import com.xixi.intelligent.utils.SPUtils
import com.xixi.intelligent.utils.TimeUtils
import com.xixi.intelligent.widget.EditTextCount
import kotlinx.android.synthetic.main.fragment_task_by_detail.*
import kotlinx.android.synthetic.main.title_normal.*
import ocom.xixi.intelligent.http.ApiClient


/**
 * 保养任务表单详情
 */
class TaskBYDetailFragment : BaseSupportFragment() {

    var taskBean:TaskBYBean? = null
    var result = ""

    override fun getContentRes(): Int {
        return R.layout.fragment_task_by_detail
    }

    companion object {
        fun newInstance(bundle: Bundle?): TaskBYDetailFragment {
            val fragment = TaskBYDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        page_title.text = "保养"
        initData()
    }

    fun initData(){
        taskBean = arguments!!.getSerializable("taskBean") as TaskBYBean
        if(taskBean!=null){
            byrw.text = taskBean?.equEquipmentMaintenance?.equipmentMaintenanceName?:""
            sbbh.text = taskBean?.equipment?.equipmentNum?:""
            sb_name.text = taskBean?.equipment?.equipmentName?:""
            start_time.text = TimeUtils.getNowString()
            tv_item_bl.text = taskBean?.equMaintenanceItem?.maintenanceItemName?:""
            item_bl.text = taskBean?.equMaintenanceItem?.remark?:""
            user_name.text = SPUtils.getInstance().getString("userName","")

        }
        select_type_group.setOnCheckedChangeListener { radioGroup, id ->
            result = radioGroup.findViewById<RadioButton>(id).tag.toString()
        }
        result = undone.tag.toString()

        remark.setOnTouchListener { v, event ->
            v.getParent().requestDisallowInterceptTouchEvent(true);
            false
        }
        remark.setOnOverMaxListener(object : EditTextCount.OnOverMaxListener {
            override fun overed() {
                toast("您已超出字数限制")
            }
        })
    }

    @OnClick(R.id.btn_submit)
    fun submit(){
        submitBYTask()
    }


    fun submitBYTask(){

        val params = ParamsUtil.init().TaskBYBody(taskBean?.id,start_time.text.toString(),remark.text.toString().trim(),result)

        ApiClient.instance.kotlinService.submitBYTask(params)
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
