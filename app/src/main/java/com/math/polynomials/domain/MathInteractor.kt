package com.math.polynomials.domain

import com.math.polynomials.models.Polynom
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MathInteractor {

    fun plus(first: String, second: String): Single<String> =
        Single.fromCallable{
            Polynom(first).plus(Polynom(second)).toString()
        }
            .subscribeOn(Schedulers.computation())

    fun multiply(first: String, second: String): Single<String> =
        Single.fromCallable{
            Polynom(first).times(Polynom(second)).toString()
        }
            .subscribeOn(Schedulers.computation())
}