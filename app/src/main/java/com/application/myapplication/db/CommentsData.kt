package com.application.myapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "commentsdata", indices = [Index(value = ["id"], unique = true)])
data class CommentsData(
    @PrimaryKey @ColumnInfo(name = "id")  var id :String,
    @ColumnInfo(name = "comments") var data:String
)