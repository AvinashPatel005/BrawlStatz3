package com.kal.brawlstatz3.data.model

data class ExpandableCardModel(var id: String?, var isExpanded:Boolean){
    constructor():this(null,false)
}