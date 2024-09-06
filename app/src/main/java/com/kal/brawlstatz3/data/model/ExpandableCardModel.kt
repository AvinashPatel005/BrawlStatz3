package com.kal.brawlstatz3.data.model

data class ExpandableCardModel(var id: Int, var isExpanded:Boolean){
    constructor():this(0,false)
}