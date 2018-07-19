package com.example.programmer.trykotlin

import com.example.programmer.trykotlin.model.RepoUserModel
import com.example.programmer.trykotlin.model.UserModel

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    private val userRepo = RepoUserModel()

    override fun printUsers() {
        userRepo.printString()
    }

    override fun request() {
        userRepo.requestAllUsersOb()
        println(userRepo.printString())
    }

    override fun getAllUsersDetails() { //
        userRepo.requestAllUsersOb().subscribe( {
            it.forEachIndexed { index, userModel ->
                println(
                        
                )
                userModel.login?.let {
                it1 -> userRepo.getUserDetailsOb(it1).subscribe( {
                    userRepo.saveUser(it, index)
            }) } }
            view.showListUsers(it)
        })
    }

    override fun start() {
            userRepo.requestAllUsersOb().subscribe({
                userRepo.saveUserList(it)
                view.showListUsers(it)}) }

    override fun getList(): List<UserModel> =
            userRepo.getUserList()

    override fun getRepo(): RepoUserModel =
            userRepo

    override fun getUser(id: Int): UserModel? =
            userRepo.getUserById(id)

    override fun getUsver() {
        println("MainPresenter getUsver")
        userRepo.getUserDetailsOb("mojtabah").subscribe({ view.showOneUser(it) }) // TODO handle error
    }

    override fun update() {
        view.updateList()

    }
}
