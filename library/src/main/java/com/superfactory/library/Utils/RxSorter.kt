package com.superfactory.library.Utils

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable


/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  18:16:26
 * @ClassName 这里输入你的类名(或用途)
 */
object RxSorter {
    fun <T> sort(list: List<T>, fun0: (T, T) -> Int, fun1: (List<T>) -> Unit) {
        Observable.fromIterable(list)
                .toSortedList{ t1, t2 ->Int
                    if (t1 == null && t2 == null) {
                        0
                    } else {
                        if (t1 == null) {
                            0.compareTo(1)
                        } else if (t2 == null) {
                            1.compareTo(0)
                        } else
                            fun0(t1, t2)
                    }
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :SingleObserver<List<T>?>{
                    override fun onSuccess(t: List<T>) {
                        fun1(t)
                    }
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }


                })
    }
}