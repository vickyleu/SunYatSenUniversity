package com.superfactory.library.Utils

import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func2

/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  18:16:26
 * @ClassName 这里输入你的类名(或用途)
 */
object RxSorter {
    fun <T> sort(list: List<T>, fun0: (T, T) -> Int, fun1: (List<T>) -> Unit) {
        rx.Observable.just(list)
                .flatMap { rx.Observable.from(it) }
                .toSortedList(object : Func2<T, T, Int> {
                    override fun call(t1: T?, t2: T?): Int {
                        if (t1 == null && t2 == null) {
                            return 0
                        } else {
                            if (t1 == null) {
                                return 0.compareTo(1)
                            } else if (t2 == null) {
                                return 1.compareTo(0)
                            } else
                                return fun0(t1, t2)
                        }
                    }
                })
                .subscribeOn(rx.schedulers.Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<T>?>() {
                    override fun onNext(t: List<T>?) {
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                    }

                })
    }
}