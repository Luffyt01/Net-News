package com.example.netnews.authentication;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.netnews.model.user.Bookmark;
import com.example.netnews.repository.Repository;

import java.util.List;

public class AuthViewModel extends AndroidViewModel {

    AuthRepository authRepository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        this.authRepository = new AuthRepository(application);
    }

    public void loginUser(String email, String password, Activity activity){
        authRepository.loginUser(email,password,activity);
    }

    public void registerUser(String name, String email, String password, Activity activity){
        authRepository.registerUser(name,email,password,activity);
    }
    public void logoutUser(Activity activity){
        authRepository.logoutUser(activity);
    }


}
