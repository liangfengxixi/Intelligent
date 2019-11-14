package com.xixi.intelligent.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import com.xixi.intelligent.R
import com.xixi.intelligent.base.BaseSupportActivity
import com.xixi.intelligent.bean.BaseBean
import com.xixi.intelligent.bean.LoginBean
import com.xixi.intelligent.common.ARConstant
import com.xixi.intelligent.http.NetworkScheduler
import com.xixi.intelligent.http.subscriber.ApiObserver
import com.xixi.intelligent.utils.KeyboardUtils
import com.xixi.intelligent.utils.L
import com.xixi.intelligent.utils.SPUtils
import com.xw.repo.XEditText
import kotlinx.android.synthetic.main.activity_login.*
import ocom.xixi.intelligent.http.ApiClient
import okhttp3.Credentials
import java.util.HashMap

@Route(path = ARConstant.AR_LoginAct)
class LoginActivity: BaseSupportActivity() {
    override fun getContentRes(): Int {
        return R.layout.activity_login
    }

    override fun initView(savedInstanceState: Bundle?) {
        edt_user.setOnXFocusChangeListener { v, hasFocus ->
            ll_user.isSelected = hasFocus
            img_user.isSelected = hasFocus
        }
        edt_psw.setOnFocusChangeListener { v, hasFocus ->
            ll_pwd.isSelected = hasFocus
            img_pwd.isSelected = hasFocus
        }
        var oldName = SPUtils.getInstance().getString("userName")
        var oldPwd = SPUtils.getInstance().getString("password")
        if(!TextUtils.isEmpty(oldName)){
            edt_user.setText(oldName)
            edt_user.setSelection(oldName.length)
        }
        if(!TextUtils.isEmpty(oldPwd)){
            edt_psw.setText(oldPwd)
            edt_psw.setSelection(oldPwd.length)
        }
    }

    @OnClick(R.id.btn_login)
    fun login(view: View) {
        val userName = edt_user.text.toString().trim()
        val pwd = edt_psw.text.toString().trim()
        if(TextUtils.isEmpty(userName)){
            toast("账号不能为空")
            return
        }
        if(TextUtils.isEmpty(pwd)){
            toast("密码不能为空")
            return
        }
        loginRequest(userName,pwd)
        KeyboardUtils.hideSoftInput(this)
//        ARouter.getInstance().build(ARConstant.AR_MainAct).navigation()
//        finish()
    }

    fun loginRequest(userName:String,pwd:String){
        val params = HashMap<String, String>()
        params.put("username", userName)
        params.put("password", pwd)
        params.put("grant_type", "password")
        val head = Credentials.basic("client", "secret")

        ApiClient.instance.kotlinService.login(head,params)
            .compose(NetworkScheduler.compose())
            .bindToLifecycle(this)
            .subscribe(object : ApiObserver<LoginBean>() {
                override fun onSuccess(t: LoginBean) {
                    if(!TextUtils.isEmpty(t.access_token)){
                        SPUtils.getInstance().put("access_token",t.access_token)
                        SPUtils.getInstance().put("userName",userName)
                        if(remember_pwd_checkbox.isChecked){
                            SPUtils.getInstance().put("password",pwd)
                        }else{
                            SPUtils.getInstance().remove("password")
                        }
                        ARouter.getInstance().build(ARConstant.AR_MainAct).navigation()
                        finish()
                    }else{
                        if(!TextUtils.isEmpty(t.error_description)){
                            toast(t.error_description)
                        }
                    }
                }
                override fun onError(e: Throwable) {
                    super.onError(e)
                    L.e(e.message)
                    toast(e.message)
                }
            })
    }
}