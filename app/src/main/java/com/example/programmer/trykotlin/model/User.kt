package com.example.programmer.trykotlin.model

data class User (
        var login: String?,
        var name: String?,
        var email: String?,
    //var image
        var starsCount: Int?,
        var companies: List<String>?,
        var repositories: List<String>?
) {
    constructor() : this("", "", "", -1, null, null)
}