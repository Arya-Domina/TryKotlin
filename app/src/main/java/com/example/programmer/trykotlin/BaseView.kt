package com.example.programmer.trykotlin

import android.view.View
import com.example.programmer.trykotlin.util.ErrorHandlerHelper

interface BaseView {

    fun start() {
        ErrorHandlerHelper.setView(this.getView())
        println("BaseView start setView ${this.getView().javaClass.name}")
    }

    fun getView(): View

}