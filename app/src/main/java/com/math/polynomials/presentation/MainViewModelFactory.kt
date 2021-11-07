package com.math.polynomials.presentation

import androidx.lifecycle.ViewModel

import android.app.Application

import androidx.lifecycle.ViewModelProvider
import com.math.polynomials.domain.MathInteractor


class MainViewModelFactory(application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(MathInteractor()) as T
        }
    }
