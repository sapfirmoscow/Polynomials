package com.math.polynomials.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MathInteractorTest{

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    private val interactor = MathInteractor()

    @Before
    fun setUp(){
        mockkStatic(Schedulers::class)

        every { Schedulers.computation() } returns Schedulers.trampoline()
    }

    @After
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun plusTest(){
         interactor.plus("x", "x").test()
             .assertComplete()
             .assertValue("2x")
    }

    @Test
    fun multiplyTest(){
        interactor.multiply("x", "x").test()
            .assertComplete()
            .assertValue("x^2")
    }
}