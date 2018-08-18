package com.android.carworkzassignment.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.android.carworkzassignment.dao.GithubUserDao;
import com.android.carworkzassignment.entity.GithubUserEntityModel;

@Database(entities = {GithubUserEntityModel.class}, version = 1, exportSchema = false)
public abstract class CarworkzDatabase extends RoomDatabase {

    private static CarworkzDatabase mInstance;

    public static CarworkzDatabase getDatabase(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), CarworkzDatabase.class, "carworkz_db").build();
        }

        return mInstance;
    }

    public abstract GithubUserDao githubUserDao();
}
