package com.example.programmer.trykotlin

import com.example.programmer.trykotlin.model.RepoUserModel
import com.example.programmer.trykotlin.model.UserModel

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {

    private val userRepo = RepoUserModel()

    override fun request() {
        userRepo.requestAllUsers()
        println(userRepo.getString())
    }

    override fun start() =
            userRepo.requestAllUsers()

    override fun getList(): List<UserModel> =
            userRepo.getAllUsers()


    override fun getUser(id: Int): UserModel? =
            userRepo.getUserById(id)

    override fun update() {
        view.updateList()

    }
}
