package com.kal.brawlstatz3.util

class FirebaseStorageUtil {
    fun getBrawlerProfileURL(id: Int) =
        "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/brawlers%2F${id}%2Fdp.webp?alt=media"

    fun getGadgetURL(id: Int, gadget: Int) =
        "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/brawlers%2F${id}%2FGD${gadget + 1}.webp?alt=media"

    fun getStarPowerURL(id: Int, starpower: Int) =
        "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/brawlers%2F${id}%2FSP${starpower + 1}.webp?alt=media"

    fun getBrawlerModel2DURL(id: Int) =
        "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/brawlers%2F${id}%2FSkin-Default.webp?alt=media"

    fun getHyperChargeURL(id: Int) =
        "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/brawlers%2F${id}%2FHypercharge.webp?alt=media"

    fun getTraitURL(trait: String) =
        "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/traits%2F${trait}.png?alt=media"

    fun getNeutralPin(id: Int) =
        "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/brawlers%2F${id}%2FPin.webp?alt=media"

    fun getGearURL(name : String) =
        "https://firebasestorage.googleapis.com/v0/b/brawlstatz-ba4cf.appspot.com/o/gears%2F${name}.webp?alt=media"

}