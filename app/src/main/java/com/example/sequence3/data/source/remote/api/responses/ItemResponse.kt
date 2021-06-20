package com.example.sequence3.data.source.remote.api.responses

class ItemResponse(
    var id: Int,
    var label: String,
    var url: String? = null,
    var checked: Int = 0
)