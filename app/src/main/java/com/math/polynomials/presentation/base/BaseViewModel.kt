package com.math.polynomials.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val rxCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        rxCompositeDisposable.clear()
    }

}