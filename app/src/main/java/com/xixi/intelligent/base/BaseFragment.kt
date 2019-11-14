package com.xixi.intelligent.base

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CheckResult
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import butterknife.ButterKnife
import butterknife.Unbinder
import com.trello.rxlifecycle3.LifecycleProvider
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.FragmentEvent
import com.trello.rxlifecycle3.android.RxLifecycleAndroid
import com.xixi.intelligent.ui.dialog.DialogUtil
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import me.yokeyword.fragmentation.SupportFragment

/**
 *
 */
abstract class BaseFragment : SupportFragment(), LifecycleProvider<FragmentEvent> {
    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()

    private var unbinder: Unbinder? = null
    internal abstract fun getContentRes(): Int
    private var progressDialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(getContentRes(), container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }


    /**
     * 加载dialog
     */
    protected fun showProgressDialog() {
        showProgressDialog("加载中...")
    }

    protected fun showProgressDialog(@StringRes resId: Int) {
        showProgressDialog(getString(resId))
    }

    protected fun showProgressDialog(tips: String) {

        try {
            if (progressDialog == null) {
                progressDialog = DialogUtil.createLoadingDialog(_mActivity, tips)
            }
            progressDialog?.show()
            //            progressDialog.setCancelable(false);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    protected fun hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog!!.isShowing()) {
                progressDialog?.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @NonNull
    @CheckResult
    override fun lifecycle(): Observable<FragmentEvent> {
        return lifecycleSubject.hide()
    }

    @NonNull
    @CheckResult
    override fun <T> bindUntilEvent(@NonNull event: FragmentEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    @NonNull
    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.lifecycleSubject.onNext(FragmentEvent.ATTACH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.lifecycleSubject.onNext(FragmentEvent.CREATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
    }

    override fun onStart() {
        super.onStart()
        this.lifecycleSubject.onNext(FragmentEvent.START)
    }

    override fun onResume() {
        super.onResume()
        this.lifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        this.lifecycleSubject.onNext(FragmentEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        this.lifecycleSubject.onNext(FragmentEvent.STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        this.lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
        super.onDestroyView()
    }

    override fun onDestroy() {
        this.lifecycleSubject.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
//        unbinder?.unbind()
    }

    override fun onDetach() {
        this.lifecycleSubject.onNext(FragmentEvent.DETACH)
        super.onDetach()
    }
}
