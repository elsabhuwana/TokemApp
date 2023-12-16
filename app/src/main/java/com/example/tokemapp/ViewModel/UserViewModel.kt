package com.example.tokemapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tokemapp.Model.UserModel
import com.example.tokemapp.Model.userlList


class UserViewModel : ViewModel() {

    var _user: MutableLiveData<MutableList<UserModel>> = MutableLiveData(userlList)

    var _userReview: MutableLiveData<MutableList<UserModel>> = MutableLiveData()

    val user: LiveData<MutableList<UserModel>>
        get() = _user

    val userReview: LiveData<MutableList<UserModel>>
        get() = _userReview
}