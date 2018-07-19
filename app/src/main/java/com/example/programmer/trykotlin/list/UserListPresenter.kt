package com.example.programmer.trykotlin.list

import com.example.programmer.trykotlin.model.RepoUserModel
import com.example.programmer.trykotlin.model.UserModel

class UserListPresenter(private val view: UserListContract.View) : UserListContract.Presenter {

    override fun printUsers() {
        RepoUserModel.instance.printString()
    }

    override fun request() {
        RepoUserModel.instance.requestAllUsersOb()
        println(RepoUserModel.instance.printString())
    }

    override fun getAllUsersDetails() { //
        RepoUserModel.instance.requestAllUsersOb().subscribe({
            it.forEachIndexed { index, userModel ->
                println(
                        
                )
                userModel.login?.let { it1 ->
                    RepoUserModel.instance.getUserDetailsOb(it1).subscribe({
                        RepoUserModel.instance.saveUser(it, index)
            }) } }
            view.showListUsers(it)
        })
    }

    override fun start() {
        RepoUserModel.instance.requestAllUsersOb().subscribe({
            RepoUserModel.instance.saveUserList(it)
            view.showListUsers(it)
        })
    }

    override fun getList(): List<UserModel> =
            RepoUserModel.instance.getUserList()

    override fun getRepo(): RepoUserModel =
            RepoUserModel.instance

    override fun getUsver() {
        println("UserListPresenter getUsver")
        RepoUserModel.instance.getUserDetailsOb("mojtabah").subscribe({ view.showOneUser(it) }) // TODO handle error
    }

    override fun update() {
        view.updateList()

    }
}
