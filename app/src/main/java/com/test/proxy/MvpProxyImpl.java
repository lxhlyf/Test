package com.test.proxy;

import com.test.inject.InjectPresenter;
import com.test.ui.base.BasePresenter;
import com.test.ui.base.BaseView;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcDarren on 2018/1/6.
 * 实现
 */
public class MvpProxyImpl<V extends BaseView> implements IMvpProxy{
    private V mView;
    private List<BasePresenter> mPresenters;
    public MvpProxyImpl(V view){
      if (mView == null){
          this.mView = view;
      }
      mPresenters = new ArrayList<>();
    }
    @Override
    public void bindAndCreatePresenter() {
        // 这个地方要去注入 Presenter 通过反射 (Dagger) soEasy
        Field[] fields = mView.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if(injectPresenter != null){
                // 创建注入
                Class<? extends BasePresenter> presenterClazz = null;

                // 编译器在运行的时候会对我们的泛型进行擦除（一般是针对系统的）
                presenterClazz = (Class) field.getType();

                if(! BasePresenter.class.isAssignableFrom(presenterClazz)){
                    // 这个 Class 是不是继承自 BasePresenter 如果不是抛异常
                    throw new RuntimeException("No support inject presenter type "+presenterClazz.getName());
                }

                BasePresenter basePresenter = null;
                try {
                    // 创建 Presenter 对象  TextView new TextView
                    basePresenter = presenterClazz.newInstance();
                    // 并没有解绑，还是会有问题，这个怎么办？1 2
                    basePresenter.attach(mView);
                    // 设置
                    field.setAccessible(true);
                    field.set(mView,basePresenter);
                    mPresenters.add(basePresenter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 检测我们的 View 层是否实现了 BasePresenter 的View接口
                checkView(basePresenter);
            }
        }
    }

    /**
     * 检测我们的 View 层是否实现了 BasePresenter 的View接口
     * @param basePresenter
     */
    private void checkView(BasePresenter basePresenter) {
        // 1. Presenter 的 View 接口
        Type[] params = ((ParameterizedType) basePresenter.getClass().getGenericSuperclass()).getActualTypeArguments();
        Class viewClazz = ((Class)params[0]);

        // 2. 要拿到 View 层的所有接口
        Class[] ViewClasses = mView.getClass().getInterfaces();
        // 3. View层没有实现就抛异常
        boolean isImplementsView = false;
        for (Class viewClass : ViewClasses) {
            if(viewClass.isAssignableFrom(viewClazz)){
                isImplementsView = true;
            }
        }
        if(!isImplementsView){
            throw new RuntimeException(mView.getClass().getSimpleName()+" must implements "+viewClazz.getName());
        }
    }

    @Override
    public void unbindPresenter() {
        // 一定要解绑
        for (BasePresenter presenter : mPresenters) {
            presenter.detach();
        }
        mView = null;
    }
}
