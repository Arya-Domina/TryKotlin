package com.example.programmer.trykotlin.list

import com.example.programmer.trykotlin.model.RepoUserModel
import com.example.programmer.trykotlin.model.UserModel
import com.example.programmer.trykotlin.util.SnackbarHelper
import retrofit2.HttpException
import java.net.UnknownHostException

class UserListPresenter(private val view: UserListContract.View) : UserListContract.Presenter {

    override fun printUsers() {
        RepoUserModel.instance.printString()
    }

    override fun request() {
    }

    override fun start() {
        if (RepoUserModel.instance.getUserList().isEmpty()) {
            RepoUserModel.instance.requestAllUsersOb().subscribe({
                RepoUserModel.instance.saveUserList(it)
                view.showListUsers(it)
            }, {
                println(it)
                val errorText: String = when (it) {
                    is UnknownHostException -> {
                        "Нет сети"
                    }
                    is HttpException -> { //404
                        it.message() + it.code()
                    }
                    else -> {
                        "Непонятная ошибка"
                    }
                }
                SnackbarHelper.showSnake(view.getView(), errorText)
                SnackbarHelper.showSnake(view.getView(), "Не удалось взять данные пользователей")
            })
        } else {
            view.showListUsers(RepoUserModel.instance.getUserList())
        }
    }

    override fun getList(): List<UserModel> =
            RepoUserModel.instance.getUserList()

    override fun getRepo(): RepoUserModel =
            RepoUserModel.instance

    override fun update() =
            view.updateList()

}
