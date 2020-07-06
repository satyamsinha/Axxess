package com.application.myapplication.network

data class TagClass(
    var name :String,
    var display_name :String,
    var followers:Int,
    var total_items :Int,
    var following :Boolean,
    var is_whitelisted :Boolean,
    var background_hash :String ,
    var thumbnail_hash :String ,
    var accent :String,
    var background_is_animated :Boolean,
    var thumbnail_is_animated :Boolean,
    var is_promoted : Boolean,
    var description :String,
    var logo_hash :String,
    var logo_destination_url :String,
    var description_annotations : DescriptionClass
)