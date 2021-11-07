package com.math.polynomials.presentation.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import androidx.lifecycle.Lifecycle

import androidx.lifecycle.LifecycleRegistry

import org.junit.Before

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import io.mockk.*

import org.junit.Rule
import org.junit.Test
import java.lang.Exception


class SingleLiveEventTest{
    // Execute tasks synchronously
    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    private val mOwner: LifecycleOwner = mockk()

    private val mEventObserver: Observer<Int?> = mockk()

    private var mLifecycle: LifecycleRegistry = mockk()

    // Event object under test
    private val mSingleLiveEvent = SingleLiveEvent<Int>()

    @Before
    @Throws(Exception::class)
    fun setUpLifecycles() {

        // Link custom lifecycle owner with the lifecyle register.
        mLifecycle = LifecycleRegistry(mOwner)

        every {
            mOwner.lifecycle
        } returns mLifecycle

        // Start observing
        mSingleLiveEvent.observe(mOwner, mEventObserver)

        // Start in a non-active state
        mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        every { mEventObserver.onChanged(any()) } just runs
    }

    @Test
    fun valueNotSet_onFirstOnResume() {
        // On resume
        mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        // no update should be emitted because no value has been set
        verify { mEventObserver wasNot called }
    }

    @Test
    fun singleUpdate_onSecondOnResume_updatesOnce() {
        // After a value is set
        mSingleLiveEvent.value = 42

        // observers are called once on resume
        mLifecycle!!.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        // on second resume, no update should be emitted.
        mLifecycle!!.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        mLifecycle!!.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        // Check that the observer is called once
        verify(exactly = 1) { mEventObserver.onChanged(any())  }
    }

    @Test
    fun twoUpdates_updatesTwice() {
        // After a value is set
        mSingleLiveEvent.value = 42

        // observers are called once on resume
        mLifecycle!!.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        // when the value is set again, observers are called again.
        mSingleLiveEvent.value = 23

        // Check that the observer has been called twice
        verify(exactly = 2) { mEventObserver.onChanged(any())  }

    }

    @Test
    fun twoUpdates_noUpdateUntilActive() {
        // Set a value
        mSingleLiveEvent.value = 42

        // which doesn't emit a change
        verify{ mEventObserver wasNot called  }


        // and set it again
        mSingleLiveEvent.value = 42

        // observers are called once on resume.
        mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        // Check that the observer is called only once
        verify(exactly = 1) { mEventObserver.onChanged(any())  }

    }
}