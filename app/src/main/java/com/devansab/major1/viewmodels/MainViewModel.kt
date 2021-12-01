package com.devansab.major1.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.devansab.major1.data.repositories.UserRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var userRepository = UserRepository(application)

    public fun isUserRegistered() {
        userRepository.isUserRegistered();
    }

    public fun getRegisterUserLiveData(): LiveData<Boolean> {
        return userRepository.getRegisterUserLiveData()
    }

}