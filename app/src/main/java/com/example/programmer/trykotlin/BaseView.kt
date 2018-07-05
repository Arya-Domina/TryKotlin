package com.example.programmer.trykotlin

import com.example.programmer.trykotlin.model.User

interface BaseView {

//    fun show()
//
//    fun hide()
//
//    fun update()

    fun showAllUsers()

    fun showOneUser(user: User)
}