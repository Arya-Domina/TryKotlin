package com.example.programmer.trykotlin.util

import android.support.design.widget.Snackbar
import android.view.View

class ErrorHandlerHelper {
    companion object {
        private var currentView: View? = null

        fun setView(view: View) {
            currentView = view
        }

        fun showSnake(errorText: String) {
            currentView?.let { Snackbar.make(it, errorText, Snackbar.LENGTH_LONG).show() }
        }

        fun showSnake(t: Throwable) {

//            val errorText: String = when (t) {
//                is UnknownHostException -> {
//                    "Нет сети"
//                }
//                is HttpException -> { //404
//                    "Нет юзера"
//                }
//                else -> {
//                    "Непонятная ошибка " + t.message
//                }
//            }
//            currentView?.let { Snackbar.make(it, errorText, Snackbar.LENGTH_LONG).show() }
            currentView?.let {
                Snackbar.make(it, t.message?.let { it } ?: "", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}