package com.application.myapplication.db

import androidx.room.*

@Dao
interface  CommentsDataDao {

    @Query("SELECT * FROM commentsdata where id = :id")
    fun getAll(id:String): CommentsData?

    @Query("SELECT * FROM commentsdata LIMIT :limit OFFSET :offset")
     fun loadDataFromDB(limit: Int, offset: Int): List<CommentsData>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(commentData:CommentsData)

    @Delete
    fun delete(commentData: CommentsData?)

    @Update
    fun update(task: CommentsData?)

}