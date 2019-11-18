package com.xixi.intelligent.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.RxLifecycle;
import com.trello.rxlifecycle3.android.FragmentEvent;
import com.trello.rxlifecycle3.android.RxLifecycleAndroid;
import com.xixi.intelligent.R;
import com.xixi.intelligent.ui.dialog.DialogUtil;
import com.xixi.intelligent.utils.L;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by xixi on 2019/10/25.
 */

public abstract class BaseSupportFragment extends SupportFragment implements LifecycleProvider<FragmentEvent> {
    @Nullable
    @BindView(R.id.page_title)
    TextView pageTitle;
    @Nullable
    @BindView(R.id.page_back)
    protected LinearLayout pageBack;

    @BindColor(R.color.colorPrimary)
    protected int color;

    private Dialog progressDialog;
//    protected MaterialDialog dialog;
    Unbinder unbinder;

    /**
     * 获取布局res
     *
     * @return res
     */
    protected abstract int getContentRes();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentRes(), container,false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (null != pageBack)
            pageBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(null != _mActivity && null != getFragmentManager()&& getFragmentManager().getBackStackEntryCount() > 1){
                        pop();
                    /*}else {
                        if (null != _mActivity && null != _mActivity.getSupportFragmentManager() && _mActivity.getSupportFragmentManager().getBackStackEntryCount() > 1) {
                            Log.e("csz", _mActivity.getSupportFragmentManager().getBackStackEntryCount() + "");
                            _mActivity.onBackPressed();
                        }*/
                    }
                }
            });
    }

    public void showPageBack(boolean visible) {
        if (pageBack == null) {
            return;
        }
        if (visible) {
            pageBack.setVisibility(View.VISIBLE);
        } else {
            pageBack.setVisibility(View.GONE);
        }
    }

    public void setPageTitle(String title) {
        if (pageTitle == null) {
            return;
        }
        pageTitle.setText(title);
    }

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
        /*if (unbinder != null) {
            unbinder.unbind();
        }*/
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    protected void goBack() {
        if (_mActivity.getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        }
    }

//    @Override
//    public boolean onBackPressedSupport() {
//        if (_mActivity.getSupportFragmentManager().getBackStackEntryCount() > 1) {
//            return super.onBackPressedSupport();
//        } else {
//            return true;
//        }
//    }

    /**
     * 加载dialog
     */
    protected void showProgressDialog() {
        showProgressDialog("加载中...");
    }

    protected void showProgressDialog(@StringRes int resId){
        showProgressDialog(getString(resId));
    }

    protected void showProgressDialog(String tips) {

        try {
            if (progressDialog == null) {
                progressDialog = DialogUtil.createLoadingDialog(_mActivity,tips);
            }
            progressDialog.show();
//            progressDialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取根部的parentFragment
     * 在fragment嵌套中，例如fragment中嵌套ViewPager， 在ViewPager的fragment中直接调用start() 无法跳转
     * 必须使用parentFragment.start()
     * @return
     */
    protected Fragment getRootParentFragment(){
        Fragment rootParentFragment = getParentFragment();
        Fragment tempFragment = rootParentFragment;
        while (tempFragment != null && tempFragment.getParentFragment() != null){
            rootParentFragment = tempFragment.getParentFragment();
            tempFragment = rootParentFragment;
        }
        return rootParentFragment;
    }

    public void show(int containerId, FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!this.isAdded()) { //  判断传入的fragment是否已经被add()过
            transaction.add(containerId, this).show(this).commit();
        } else {
            transaction.show(this).commit();
        }
    }

    public void hide(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (this.isAdded()) { //  判断传入的fragment是否已经被add()过
            transaction.hide(this).commit();
        }
    }

    public void removeFragment(Fragment fg, FragmentManager fragmentManager){
        if(fg == null || fragmentManager == null){
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //如果之前没有添加过
        if(!fg.isAdded()){
            return;
        }
        transaction.remove(fg);
        transaction.commit();
    }

    public void removeFragment(Fragment fg){
        if(fg == null){
            return;
        }
        removeFragment(fg, fg.getFragmentManager());
    }

    /**
     * 显示Toast
     */
    public void toast(String pMsg) {
        Toast.makeText(_mActivity, pMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast
     */
    public void toast(int pMsg) {
        Toast.makeText(_mActivity, pMsg, Toast.LENGTH_SHORT).show();
    }

}