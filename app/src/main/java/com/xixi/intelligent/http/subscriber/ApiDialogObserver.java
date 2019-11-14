package com.xixi.intelligent.http.subscriber;

import android.content.Context;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * @Description: API统一订阅者，采用弱引用管理上下文，防止内存泄漏
 * @author: jeasinlee
 * @date: 2017-01-03 14:07
 */
public abstract class ApiDialogObserver<T> implements Observer<T> {
    public WeakReference<Context> contextWeakReference;

//    MaterialDialog dialog;

    public ApiDialogObserver(Context context) {
        contextWeakReference = new WeakReference<Context>(context);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        Context ctx = contextWeakReference.get();
//        if(null != ctx){
//            if(dialog == null){
//                dialog = new MaterialDialog.Builder(ctx).progress(true,0).content("加载中")
//                        .widgetColor(ctx.getResources().getColor(R.color.cl_text_title)).canceledOnTouchOutside(false).show();
//            }else{
//                dialog.show();
//            }
//        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
//        if(null != dialog){
//            dialog.dismiss();
//            onError(e.getMessage());
//        }
        onError(e.getMessage());
    }

    @Override
    public void onComplete() {
//        if(null != dialog){
//            dialog.dismiss();
//        }
    }

    public abstract void onSuccess(T t);

    public void onError(String msg){

    }

}
