package com.test.proxy;

import com.test.ui.base.BaseView;

/**
 * Created by 简言 on 2019/2/4.
 * 努力吧 ！ 少年 ！
 */

public class BaseFragmentProxyImpl extends MvpProxyImpl implements IBaseFragmentProxy {
    public BaseFragmentProxyImpl(BaseView view) {
        super(view);
    }
}
