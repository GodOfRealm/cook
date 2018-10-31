package com.miguan.youmi.core.base;


import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lmy on 2017/1/14.
 */

public abstract class ItemPresenter<T> {

    private Type mRawType;

    public ItemPresenter() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("ParameterizedType objects must be instantiated with a type parameter. Ex: new ParameterizedType<MyModel<MyOtherModel>>() { }");
        }

        Type type = ((java.lang.reflect.ParameterizedType)superclass).getActualTypeArguments()[0];
        mRawType = type;
    }

    public Type getRawType() {
        return mRawType;
    }

    public int getItemType() {
        return mRawType.hashCode();
    }

    public void onViewDetached(ViewHolder holder){}

    public abstract int getLayoutRes();

    public abstract void convert(ViewHolder holder, T t);

    public void convert(ViewHolder holder, T t, List<Object> payloads){}
}
