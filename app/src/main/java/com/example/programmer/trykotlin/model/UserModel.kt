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
                @SerializedName("name")
                @Expose
                var name: String?,
                @SerializedName("avatar_url")
                @Expose
                var avatarUrl:String?,
                @SerializedName("email")
                @Expose
                var email:String?,
                @SerializedName("company")
                @Expose
                var company:String?,
                @SerializedName("public_repos")
                @Expose
                var repositoriesCount:Int? = 0,
                var hasDetails: Boolean = false): Serializable {

    constructor() : this("", 0, "", "", "", "", 0, false)

    constructor(username: String) : this(username, 0, "", "", "", "", 0, false)

    override fun toString(): String {
        return "{login: $login, id: $id, name: $name, avatarUrl: $avatarUrl, email: $email, company: $company, repositoriesCount: $repositoriesCount} \n"
    }
}

//@SerializedName("login")
//@Expose
//var login:String
//@SerializedName("id")
//@Expose
//var id:Int = 0
//@SerializedName("node_id")
//@Expose
//var nodeId:String
//@SerializedName("avatar_url")
//@Expose
//var avatarUrl:String
//@SerializedName("gravatar_id")
//@Expose
//var gravatarId:Any
//@SerializedName("url")
//@Expose
//var url:String
//@SerializedName("html_url")
//@Expose
//var htmlUrl:String
//@SerializedName("followers_url")
//@Expose
//var followersUrl:String
//@SerializedName("following_url")
//@Expose
//var followingUrl:String
//@SerializedName("gists_url")
//@Expose
//var gistsUrl:String
//@SerializedName("starred_url")
//@Expose
//var starredUrl:String
//@SerializedName("subscriptions_url")
//@Expose
//var subscriptionsUrl:String
//@SerializedName("organizations_url")
//@Expose
//var organizationsUrl:String
//@SerializedName("repos_url")
//@Expose
//var reposUrl:String
//@SerializedName("events_url")
//@Expose
//var eventsUrl:String
//@SerializedName("received_events_url")
//@Expose
//var receivedEventsUrl:String
//@SerializedName("type")
//@Expose
//var type:String
//@SerializedName("site_admin")
//@Expose
//var siteAdmin:Boolean = false