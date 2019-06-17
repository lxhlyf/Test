package com.test.ui.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 简言 on 2019/2/3.
 * 努力吧 ！ 少年 ！
 */

public class BasePresenter<V extends BaseView, M extends BaseModel> {

    private V mView;
    private V mProxyView;
    private M mModel;

    private CompositeSubscription mCompositeSubscription;


    public void attach(final V view) {
        if (mView == null) {
            this.mView = view;
        }

        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if (mView == null) {
                    return null;
                }
                return method.invoke(mView, args);
            }
        });

        //实例化model层  拿到类上继承的类，和实现的接口
        Type[] params = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        //Log.i("params—— ：", params[0]+"/"+params[1]);
        try {
            // 最好要判断一下类型
            Class<? extends BaseView> modelClass = (Class<? extends BaseView>) params[0];
            if (!BaseView.class.isAssignableFrom(modelClass)) {
                // 这个 Class 是不是继承自 BasePresenter 如果不是抛异常
                throw new RuntimeException("No support inject presenter type " + modelClass.getName());
            }
            mModel = (M) ((Class) params[1]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void addSubscription(Observable observable, Subscriber subscriber) {

        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    private void unSubscribe(){
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    protected M getModel() {
        return mModel;
    }

    protected V getView() {
        return mProxyView;
    }

    public void detach() {
        if (mView != null) {
            mView = null;
        }
        unSubscribe();
    }
}
