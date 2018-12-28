package com.common.cook.app;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Desc: Observer简单实现类
 * <p>
 * Author: SonnyJack
 * Date: 2018-06-28
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 *
 * @param <T> the type parameter
 */
public class DefaultObserverImp<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

}
