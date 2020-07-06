package com.application.myapplication.network

data class AdConfig(
    var safeFlags :ArrayList<String>,
    var highRiskFlags:ArrayList<String>,
    var unsafeFlags:ArrayList<String>,
    var wallUnsafeFlags:ArrayList<String>,
    var showsAds :Boolean


)