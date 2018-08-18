package com.android.carworkzassignment.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.android.carworkzassignment.database.CarworkzDatabase;
import com.android.carworkzassignment.entity.GithubUserEntityModel;
import com.android.carworkzassignment.repositories.GithubUserRepository;

import java.util.List;

public class GithubUserDataModel extends AndroidViewModel {

    private CarworkzDatabase appDatabase;

    public GithubUserDataModel(@NonNull Application application) {
        super(application);
        appDatabase = CarworkzDatabase.getDatabase(getApplication());
    }

    public void loadAllGithubUser(int userSince){
        GithubUserRepository userRepository = new GithubUserRepository(getApplication(),appDatabase.githubUserDao());
        userRepository.getGithubUsers(userSince);
    }

    public LiveData<List<GithubUserEntityModel>> getAllGithubUser() {
        return appDatabase.githubUserDao().getAllGithubUser();
    }

    public LiveData<List<GithubUserEntityModel>> filterGithubUser(String filterUser) {
        return appDatabase.githubUserDao().filterUserByName(filterUser);
    }

    public void addGithubUser(GithubUserEntityModel githubUserModel) {
        new AddGithubUserAsyncTask(appDatabase).execute(githubUserModel);
    }

    private static class AddGithubUserAsyncTask extends AsyncTask<GithubUserEntityModel, Void, Void> {

        private CarworkzDatabase database;

        private AddGithubUserAsyncTask(CarworkzDatabase appDatabase) {
            this.database = appDatabase;
        }

        @Override
        protected Void doInBackground(GithubUserEntityModel... githubUserEntityModels) {
            Long id=  database.githubUserDao().addGithubUser(githubUserEntityModels[0]);
            return null;
        }
    }

    public void deleteGithubUser(GithubUserEntityModel userEntityModel) {
        new DeleteGithubUserAsyncTask(appDatabase).execute(userEntityModel);
    }

    private static class DeleteGithubUserAsyncTask extends AsyncTask<GithubUserEntityModel, Void, Void> {
        CarworkzDatabase database;

        private DeleteGithubUserAsyncTask(CarworkzDatabase appDatabase) {
            this.database = appDatabase;
        }

        @Override
        protected Void doInBackground(GithubUserEntityModel... userEntityModel) {
            database.githubUserDao().deleteGithubUser(userEntityModel[0]);
            return null;
        }
    }
}
