package com.xixi.intelligent.http.subscriber;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * @Description: API统一订阅者，采用弱引用管理上下文，防止内存泄漏
 * @author: jeasinlee
 * @date: 2017-01-03 14:07
 */
public abstract class ApiObserver<T> implements Observer<T> {
//    public WeakReference<Context> contextWeakReference;

    public ApiObserver() {
//        contextWeakReference = new WeakReference<Context>(context);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onError(e.getMessage());
    }

    @Override
    public void onComplete() {
    }

    public abstract void onSuccess(T t);

    public void onError(String msg) {

    }


    public static class MyThrowable extends Throwable {

        private int errorCode = -1;
        public MyThrowable(int errorCode, String message) {
            super(message);
            this.errorCode = errorCode;
        }

        public int getErrorCode() {
            return errorCode;
        }
    }

}
