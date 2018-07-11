package com.example.programmer.trykotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchResultModel(@SerializedName("total_count")
                        @Expose
                        var totalCount: Int = 0,
                        @SerializedName("incomplete_results")
                        @Expose
                        var incompleteResult: Boolean = false,
                        @SerializedName("items")
                        @Expose
                        var items: List<UserModel> = listOf()) {

}