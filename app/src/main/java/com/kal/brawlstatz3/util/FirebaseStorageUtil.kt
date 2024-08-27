package com.kal.brawlstatz3.util

class FirebaseStorageUtil {
    fun getBrawlerProfileURL(id:Int):String{
        return "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/brawlers%2F${id}%2Fdp.webp?alt=media"
    }
    fun getGadgetURL(id:Int, gadget:Int):String{
        return "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/brawlers%2F${id}%2FGD${gadget+1}.webp?alt=media"
    }
    fun getStarPowerURL(id:Int, starpower:Int):String{
        return "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/brawlers%2F${id}%2FSP${starpower+1}.webp?alt=media"
    }
    fun getBrawlerModel2DURL(id:Int):String{
        return "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/brawlers%2F${id}%2FSkin-Default.webp?alt=media"
    }
    fun getTraitURL(trait:String):String{
        return "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/traits%2F${trait}.png?alt=media"
    }
    fun getNeutralPin(id:Int):String{
        return "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/brawlers%2F${id}%2FPin.webp?alt=media"
    }
}