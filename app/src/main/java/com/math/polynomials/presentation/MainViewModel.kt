package com.math.polynomials.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.math.polynomials.domain.MathInteractor
import com.math.polynomials.ext.addTo
import com.math.polynomials.presentation.base.BaseViewModel
import com.math.polynomials.presentation.base.SingleLiveEvent

class MainViewModel(
    private val interactor: MathInteractor
) : BaseViewModel() {

    private val error = SingleLiveEvent<Unit>()
    private val result = MutableLiveData<String>()
    fun getFirstLiveData(): LiveData<String> = result
    fun getErrorLiveData(): LiveData<Unit> = error

    fun calculatePoly(s1: String, s2: String) =
        interactor.plus(s1, s2)
            .subscribe({ result.postValue(it) }, ::onError)
            .addTo(rxCompositeDisposable)


    fun multiplyPoly(s1: String, s2: String) =
        interactor.multiply(s1, s2)
            .subscribe({ result.postValue(it) }, ::onError)
            .addTo(rxCompositeDisposable)


    private fun onError(throwable: Throwable){
        error.postValue(Unit)
    }

}