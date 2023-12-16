package com.example.tokemapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tokemapp.Model.ReviewModel
import com.example.tokemapp.Model.listReview
import com.example.tokemapp.Model.userReviewList

class ReviewViewModel : ViewModel() {
    var _listReview : MutableLiveData<MutableList<ReviewModel>> = MutableLiveData(userReviewList)

    val listReview: LiveData<MutableList<ReviewModel>>
        get() = _listReview
}