package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.bean.EquipBean
import kotlinx.android.synthetic.main.fragment_task_sblb_detail.*
import kotlinx.android.synthetic.main.title_normal.*


/**
 * 设备详情
 */
class TaskSBLBDetailFragment : BaseSupportFragment() {

    var taskBean: EquipBean? = null

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
        taskBean = arguments!!.getSerializable("taskBean") as EquipBean
        if(taskBean!=null){
            tv_name.text = taskBean?.equipmentType?.equipmentTypeName?:""
            tv_1.text = taskBean?.equipmentName?:""
            tv_2.text = taskBean?.equipmentNum?:""
            tv_3.text = taskBean?.workshop?.name?:""
            tv_4.text = taskBean?.model?:""
            tv_5.text = taskBean?.firstUsedTime?:""

            tv_11.text = taskBean?.equipmentType?.equipmentTypeName?:""
            setStatus(taskBean?.status)
            tv_13.text = taskBean?.manufacturer?.suppllierName?:""
            tv_14.text = taskBean?.dataOfEntry?:""

            tv_remark.text = taskBean?.remark?:""
        }
    }

    fun setStatus(status:String?){
        when(status){
            "USEING"->{
                tv_12.text = "启用中"
                tv_12.setTextColor(ContextCompat.getColor(_mActivity,R.color.brown))
            }
            "UNUSED"->{
                tv_12.text = "停用中"
                tv_12.setTextColor(ContextCompat.getColor(_mActivity,R.color.equipment_status_unused))
            }
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
