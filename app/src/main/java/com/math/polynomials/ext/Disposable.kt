package com.math.polynomials.ext

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.addTo(compositeDisposable: CompositeDisposable) = apply { compositeDisposable.add(this) }