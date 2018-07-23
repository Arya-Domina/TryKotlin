package com.example.programmer.trykotlin.util

import android.support.design.widget.Snackbar
import android.view.View

class SnackbarHelper { //TODO Handle errors!
    companion object {
        fun showSnake(view: View, errorText: String) {
            Snackbar.make(view, errorText, Snackbar.LENGTH_LONG).show()
        }
    }
}