package com.xixi.intelligent.ui.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import butterknife.OnClick
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportFragment
import com.xixi.intelligent.bean.BaseBean
import com.xixi.intelligent.bean.BaseListBean
import com.xixi.intelligent.bean.FaultItemBean
import com.xixi.intelligent.bean.SBBXNameBean
import com.xixi.intelligent.http.NetworkScheduler
import com.xixi.intelligent.http.ParamsUtil
import com.xixi.intelligent.http.subscriber.ApiObserver
import kotlinx.android.synthetic.main.fragment_task_sbbx.*
import kotlinx.android.synthetic.main.title_normal.*
import ocom.xixi.intelligent.http.ApiClient
import org.angmarch.views.OnSpinnerItemSelectedListener
import org.angmarch.views.SpinnerTextFormatter


/**
 * 设备报修
 */
class TaskSBBXFragment : BaseSupportFragment() {

    var mSBBXNameBean:SBBXNameBean? = null
    var mFaultItemBean:FaultItemBean? = null
    var mFaultList= arrayListOf<FaultItemBean>()
    var result = ""

    override fun getContentRes(): Int {
        return R.layout.fragment_task_sbbx
    }

    companion object {
        fun newInstance(): TaskSBBXFragment {
            return TaskSBBXFragment()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        page_title.text = "设备报修"
        initEditText()
        initData()
    }

    fun initEditText(){
        sbbh.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND
                || actionId == EditorInfo.IME_ACTION_DONE
                || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                //处理事件
                getEquipName()
            }
            false
        }
    }

    fun initSpinner(){
        val textFormatter: SpinnerTextFormatter<FaultItemBean> =
            SpinnerTextFormatter<FaultItemBean> { bean ->
                SpannableString(bean.faultItemName)
            }

        spinner.setSpinnerTextFormatter(textFormatter)
        spinner.setSelectedTextFormatter(textFormatter)
        spinner.onSpinnerItemSelectedListener =
            OnSpinnerItemSelectedListener { parent, view, position, id ->
                mFaultItemBean = spinner.selectedItem as FaultItemBean

            }
        spinner.attachDataSource(mFaultList)
    }

    fun initData(){
        getAllFaultItem()

        remark.setOnTouchListener { v, event ->
            v.getParent().requestDisallowInterceptTouchEvent(true);
            false
        }
        remark.setOnOverMaxListener {
            toast("您已超出字数限制") }
    }

    @OnClick(R.id.img_scan)
    fun scan(){
    }


    @OnClick(R.id.btn_submit)
    fun submit(){
        submitTask()
    }


    //获取设备名
    fun getEquipName(){

        val params = sbbh.text.toString().trim()

        ApiClient.instance.kotlinService.getEquipName(params)
            .compose(NetworkScheduler.compose())
            .bindToLifecycle(this)
            .subscribe(object : ApiObserver<BaseBean<SBBXNameBean>>() {
                override fun onSuccess(t: BaseBean<SBBXNameBean>) {
                    if(t.isSuccess()){
                        mSBBXNameBean = t.data
                        sb_name.text = mSBBXNameBean?.equipmentName?:""
                    }else{
                        toast(t.msg?:"")
                    }
                }

                override fun onError(msg: String?) {
                    toast(msg)
                }
            })
    }

    //获取所有不良项
    private fun getAllFaultItem(){

        ApiClient.instance.kotlinService.getAllFaultItem()
            .compose(NetworkScheduler.compose())
            .bindToLifecycle(this)
            .subscribe(object : ApiObserver<BaseListBean<FaultItemBean>>() {
                override fun onSuccess(t: BaseListBean<FaultItemBean>) {
                    if(t.isSuccess()){
                        if(!t.data.isNullOrEmpty()){
                            mFaultList.addAll(t.data)
                            mFaultItemBean = mFaultList.get(0)
                            initSpinner()
                        }
                    }else{
                        toast(t.msg?:"")
                    }
                }

                override fun onError(msg: String?) {
                    toast(msg)
                }
            })
    }

    //提交报修
    private fun submitTask(){

        if(TextUtils.isEmpty(bxm.text.toString())){
            toast("设备编号不能为空")
            return
        }
        val params = ParamsUtil.init().TaskSBBXBody(bxm.text.toString().trim(),mSBBXNameBean?.id,mFaultItemBean?.id,
            remark.text.toString())

        ApiClient.instance.kotlinService.submitSBBXTask(params)
            .compose(NetworkScheduler.compose())
            .bindToLifecycle(this)
            .subscribe(object : ApiObserver<BaseBean<Any?>>() {
                override fun onSuccess(t: BaseBean<Any?>) {
                    if(t.isSuccess()){
                        toast("提交成功")
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
