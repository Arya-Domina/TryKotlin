package com.example.programmer.trykotlin.details

import com.example.programmer.trykotlin.model.RepoUserModel
import com.example.programmer.trykotlin.util.SnackbarHelper
import retrofit2.HttpException
import java.net.UnknownHostException

class UserDetailsPresenter(private val view: UserDetailsContract.View) : UserDetailsContract.Presenter {
    init {
        println("init UserDetailsPresenter")
    }

    override fun start() {
    }

    override fun getUsver(username: String) {
        if ((RepoUserModel.instance.getUserByUsername(username) != null) && RepoUserModel.instance.getUserByUsername(username)!!.hasDetails) {
            view.bindUsver(RepoUserModel.instance.getUserByUsername(username))
        }
        RepoUserModel.instance.requestUserDetailsOb(username).subscribe({
            view.bindUsver(it)
            RepoUserModel.instance.saveUserById(it)
        }, {
            //                println("Throwable: $it")
//                it.printStackTrace()
//                println("message: ${it.message}")
            val errorText: String = when (it) {
                is UnknownHostException -> {
                    "Нет сети"
                }
                is HttpException -> { //404
                    "Нет юзера"
                }
                else -> {
                    "Непонятная ошибка"
                }
            }
            SnackbarHelper.showSnake(view.getView(), errorText)
        })
    }
}