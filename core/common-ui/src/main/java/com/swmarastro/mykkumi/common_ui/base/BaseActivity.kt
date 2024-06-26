package com.swmarastro.mykkumi.common_ui.base

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T: ViewDataBinding>(
    @LayoutRes private val layoutId: Int
) : AppCompatActivity(){
    lateinit var binding: T

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        setContentView(binding.root)
    }

    abstract suspend fun initView()

    protected inline fun bind(block: T.() -> Unit) {
        binding.apply(block)
    }

    override fun onRestart() {
        super.onRestart()
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        binding.unbind()
        super.onDestroy()
    }
}