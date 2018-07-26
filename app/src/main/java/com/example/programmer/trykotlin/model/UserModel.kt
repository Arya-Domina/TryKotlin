package com.example.programmer.trykotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserModel(@SerializedName("login")
                @Expose
                var login: String?,
                @SerializedName("id")
                @Expose
                var id:Int? = 0,
                @SerializedName("type")
                @Expose
                var type: String?,
                @SerializedName("name")
                @Expose
                var name: String?,
                @SerializedName("avatar_url")
                @Expose
                var avatarUrl:String?,
                @SerializedName("email")
                @Expose
                var email:String?,
                @SerializedName("location")
                @Expose
                var location: String?,
                @SerializedName("company")
                @Expose
                var company:String?,
                @SerializedName("public_repos")
                @Expose
                var repositoriesCount:Int? = 0,
                var hasDetails: Boolean = false): Serializable {

    constructor() : this("", 0, "", null, null, null, null, null, null, false)

    constructor(username: String) : this(username, 0, "", null, null, null, null, null, null, false)

    override fun toString(): String {
        return "{login: $login, id: $id, type: $type, name: $name, avatarUrl: $avatarUrl, email: $email, location: $location, company: $company, repositoriesCount: $repositoriesCount} \n"
    }
}
