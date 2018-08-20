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
        return "RepoModel(id=$id, nodeId=$nodeId, name=$name, fullName=$fullName, user=$user, " +
                "private=$private, htmlUrl=$htmlUrl, description=$description, fork=$fork, " +
                "createdAt=$createdAt, updatedAt=$updatedAt, pushedAt=$pushedAt, size=$size, " +
                "stargazersCount=$stargazersCount, watchersCount=$watchersCount, language=$language)"
    }


}

//    @SerializedName("has_issues")
//    @Expose
//    var hasIssues: Boolean = false
//    @SerializedName("has_projects")
//    @Expose
//    var hasProjects: Boolean = false
//    @SerializedName("has_downloads")
//    @Expose
//    var hasDownloads: Boolean = false
//    @SerializedName("has_wiki")
//    @Expose
//    var hasWiki: Boolean = false
//    @SerializedName("has_pages")
//    @Expose
//    var hasPages: Boolean = false
//    @SerializedName("forks_count")
//    @Expose
//    var forksCount: Int = 0
//    @SerializedName("archived")
//    @Expose
//    var archived: Boolean = false
//    @SerializedName("open_issues_count")
//    @Expose
//    var openIssuesCount: Int = 0
//    @SerializedName("license")
//    @Expose
//    var license: Any? = null
//    @SerializedName("forks")
//    @Expose
//    var forks: Int = 0
//    @SerializedName("open_issues")
//    @Expose
//    var openIssues: Int = 0
//    @SerializedName("watchers")
//    @Expose
//    var watchers: Int = 0
//    @SerializedName("default_branch")
//    @Expose
//    var defaultBranch: String? = null
//    @SerializedName("permissions")
//    @Expose
//    var permissions: Permissions? = null