package com.wubq.reactive.service.reactor;

/**
 * @author wubq
 * @since 2022/3/20 23:41
 */
public interface Callback<T> {
    public void onSuccess(T t);

    public void onError(Throwable error);
}
