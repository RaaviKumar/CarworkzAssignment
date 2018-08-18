package com.android.carworkzassignment.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.android.carworkzassignment.entity.GithubUserEntityModel;

import java.util.List;

@Dao
public interface GithubUserDao {

    @Query("select * from GithubUserEntityModel")
    LiveData<List<GithubUserEntityModel>> getAllGithubUser();

    @Query("select * from GithubUserEntityModel where id = :id")
    GithubUserEntityModel getGithubUserById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addGithubUser(GithubUserEntityModel githubUserEntityModel);

    @Delete
    void deleteGithubUser(GithubUserEntityModel githubUserEntityModel);

    @Query("SELECT * FROM GithubUserEntityModel WHERE login LIKE '%' || :search || '%' ")
    LiveData<List<GithubUserEntityModel>> filterUserByName(String search);

}
