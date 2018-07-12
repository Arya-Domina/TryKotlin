package com.example.programmer.trykotlin

import com.example.programmer.trykotlin.model.User

interface BaseView<T> {

    fun setPresenter(presenter: T)

    fun start()

//    fun show()
//
//    fun hide()
//
//    fun update()

//    fun showAllUsers()

//    fun showOneUser(user: User)
}