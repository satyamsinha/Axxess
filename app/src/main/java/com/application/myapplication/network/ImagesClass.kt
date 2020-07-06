package com.application.myapplication.network

data class ImagesClass(

    var id: String,
    var title: String,
    var description: String,
    var datetime :Int,
    var type: String,
    var animated :Boolean,
    var width:Int,
    var height :Int,
    var size :Int,
    var views :Int,
    var bandwidth:Long,
    var vote:String,
    var favorite : Boolean,
    var nsfw:String,
    var section:String ,
    var account_url :String,
    var account_id:String,
    var is_ad:Boolean,
    var in_most_viral: Boolean,
    var has_sound :Boolean,
    var tags : ArrayList<String>,
    var ad_type :Int,
    var ad_url :  String,
    var edited : String,
    var in_gallery :Boolean,
    var link :String,
    var comment_count :Int,
    var favorite_count :Int,
    var ups :String,
    var downs: String,
    var points: String,
    var score: String




)