package com.kal.brawlstatz3.data.model

import com.kal.brawlstatz3.data.model.AppState

data class News(
    val id: String,
    val title: String,
    val description: String?,
    val date: Int,
    val image: String?,
    val link: String?
){
    constructor() : this("","",null,0,null,null)
}
