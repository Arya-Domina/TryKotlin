package com.example.programmer.trykotlin.model

class Repo() {
    private var userList = arrayListOf<User>()

    fun addUser(user: User) = userList.add(user)

    fun saveUser(id: Int, user: User) = userList.set(id, user)

    fun getUserById(id: Int) = userList[id]

    fun getAllUsers() = userList

    fun removeUserById(id: Int) = userList.removeAt(id)

    fun createNewUsers(): ArrayList<User> {
        userList = arrayListOf(
                User("Vasy", "First", "@mail", 42, listOf("company", "other company"), listOf("new repository")),
                User("Lesh", "Second", "@mail", 13, listOf("other company"), listOf("new repository")),
                User("Tuck", "Third", "@mail", 666, listOf("company", "other company"), listOf("repository")),
                User("Red", "fourth", "@mail", 7, listOf("company"), listOf("repository", "new repository")),
                User("Wello", "fifth", "@mail", 5, listOf("company", "other company"), listOf("repository", "new repository", "third repo"))
        )
        return userList
    }

}