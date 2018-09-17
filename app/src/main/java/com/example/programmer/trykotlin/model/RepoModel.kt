package com.example.programmer.trykotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RepoModel(@SerializedName("id") @Expose
           var id: Int = 0,
                @SerializedName("node_id") @Expose
           var nodeId: String? = null,
                @SerializedName("name") @Expose
           var name: String? = null,
                @SerializedName("full_name") @Expose
           var fullName: String? = null,
                @SerializedName("owner") @Expose
           var user: UserModel? = null,
                @SerializedName("private") @Expose
           var private: Boolean = false,
                @SerializedName("html_url") @Expose
           var htmlUrl: String? = null,
                @SerializedName("description") @Expose
           var description: String? = null,
                @SerializedName("fork") @Expose
           var fork: Boolean = false,
                @SerializedName("created_at") @Expose
           var createdAt: String? = null,
                @SerializedName("updated_at") @Expose
           var updatedAt: String? = null,
                @SerializedName("pushed_at") @Expose
           var pushedAt: String? = null,
                @SerializedName("size") @Expose
           var size: Int = 0,
                @SerializedName("stargazers_count") @Expose
           var stargazersCount: Int = 0,
                @SerializedName("watchers_count") @Expose
           var watchersCount: Int = 0,
                @SerializedName("language") @Expose
           var language: String? = null): Serializable {
    constructor() : this(0, null, null, null, null, false,
            null, null, false, null, null, null,
            0, 0, 0, null)

    override fun toString(): String {
        return "RepoModel(id=$id, name=$name, user=${user?.login}, " +
                "private=$private, description=$description, " +
                "createdAt=$createdAt, size=$size, " +
                "stargazersCount=$stargazersCount, language=$language)\n"
    }
//    override fun toString(): String {
//        return "RepoModel(id=$id, nodeId=$nodeId, name=$name, fullName=$fullName, user=$user, " +
//                "private=$private, htmlUrl=$htmlUrl, description=$description, fork=$fork, " +
//                "createdAt=$createdAt, updatedAt=$updatedAt, pushedAt=$pushedAt, size=$size, " +
//                "stargazersCount=$stargazersCount, watchersCount=$watchersCount, language=$language)"
//    }
}
