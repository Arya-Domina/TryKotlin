package com.example.programmer.trykotlin.util

import android.support.design.widget.Snackbar
import android.view.View
import com.example.programmer.trykotlin.R
import retrofit2.HttpException
import java.net.UnknownHostException

class ErrorHandlerHelper {
    companion object {
        private var currentView: View? = null

        fun setView(view: View) {
            currentView = view
        }

        fun showSnake(t: Throwable) {

            when (t) {
                is UnknownHostException -> {
                    currentView?.let {
                        Snackbar.make(it, it.context.getString(R.string.error_no_connection), Snackbar.LENGTH_LONG).show()
                    }
                }
                is HttpException -> {
                    when (t.code()) {
                        401 -> {
                            currentView?.let {
                                Snackbar.make(it, it.context.getString(R.string.error_401), Snackbar.LENGTH_LONG)
                                        .setAction("now", { _ ->
                                            val helper = AlertDialogHelper()
                                            helper.showGetterToken(it.context)
                                        })
                                        .show()
                            }
                        }
                        404 -> {
                            currentView?.let {
                                Snackbar.make(it, it.context.getString(R.string.error_404), Snackbar.LENGTH_LONG).show()
                            }
                        }
                        else -> {
                            currentView?.let {
                                Snackbar.make(it, t.message ?: "", Snackbar.LENGTH_LONG).show()
                            }
                        }
                    }
                }
                else -> {
                    currentView?.let {
                        Snackbar.make(it, t.message ?: "", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}