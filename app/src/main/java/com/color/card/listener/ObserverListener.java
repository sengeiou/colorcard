package com.color.card.listener;

import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;


public interface ObserverListener<T> {

    void onSubscribe(Disposable disposable);

    void onNext(@Nullable T result);

    void onError(String msg);
}
