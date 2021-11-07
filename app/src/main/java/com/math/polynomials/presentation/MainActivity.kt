package com.math.polynomials.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.math.polynomials.R
import com.math.polynomials.databinding.ActivityMainBinding
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var viewmodel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel = MainViewModelFactory(application).create(MainViewModel::class.java)

        observe()
        setListeners()
    }

    private fun observe(){
        viewmodel.getFirstLiveData().observe(this, {
            binding.result.text = it
        })

        viewmodel.getErrorLiveData().observe(this, {showError()})
    }

    private fun setListeners(){
        binding.plusButton.setOnClickListener { onPlusClick() }
        binding.multiplyButton.setOnClickListener { onMultiplyClick() }
    }

    private fun onPlusClick(){
        viewmodel.calculatePoly(binding.firstpolyedit.text.toString(), binding.secondpolyedit.text.toString())
        hideKeyboard()
    }

    private fun onMultiplyClick(){
        viewmodel.multiplyPoly(binding.firstpolyedit.text.toString(), binding.secondpolyedit.text.toString())
        hideKeyboard()
    }

    private fun hideKeyboard(){
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showError(){
        Snackbar.make(binding.root, "Something go wrong", LENGTH_SHORT).show()
    }

}