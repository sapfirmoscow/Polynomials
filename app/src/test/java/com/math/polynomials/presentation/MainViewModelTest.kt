package com.math.polynomials.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.math.polynomials.domain.MathInteractor
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    private val interactor: MathInteractor = mockk()
    private val viewModel = MainViewModel(interactor)

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun calculatePolyTest() {
        val s1 = "x^2"
        val s2 = "x^2"
        val expected = "2x^2"

        every { interactor.plus(s1, s2) } returns Single.just(expected)

        viewModel.calculatePoly(s1, s2)

        Assert.assertEquals(expected, viewModel.getFirstLiveData().value)
    }

    @Test
    fun multiplyPolyTest() {
        val s1 = "x^2"
        val s2 = "x^2"
        val expected = "x^4"

        every { interactor.multiply(s1, s2) } returns Single.just(expected)

        viewModel.multiplyPoly(s1, s2)

        Assert.assertEquals(expected, viewModel.getFirstLiveData().value)
    }

    @Test
    fun onError(){
        every { interactor.multiply(any(), any()) } returns Single.error(IllegalArgumentException())

        viewModel.multiplyPoly("sadsad", "x^2")

        Assert.assertEquals(Unit, viewModel.getErrorLiveData().value)
    }

}