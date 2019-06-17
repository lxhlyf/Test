package com.test.proxy;


import com.test.ui.base.BaseView;

/**
 * Created by hcDarren on 2018/1/6.
 */

public class BaseActivityProxyImpl<V extends BaseView> extends MvpProxyImpl<V> implements IBaseActivityProxy{

    public BaseActivityProxyImpl(V view) {
        super(view);
    }
    // 不同对待，一般可能不会


}
