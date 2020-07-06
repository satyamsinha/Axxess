package com.application.myapplication.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {CommentsData.class}, version = 1)
public abstract class CommentsDatabase extends RoomDatabase {

    public abstract CommentsDataDao commentsDataDao();
    private static CommentsDatabase INSTANCE;

    // on calling instance will create DB
    public static CommentsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CommentsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    CommentsDatabase.class,
                                    "Comments").build();
                }
            }
        }
        return INSTANCE;
    }
}