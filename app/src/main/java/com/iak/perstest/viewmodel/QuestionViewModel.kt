package com.iak.perstest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.iak.perstest.repo.QuestionRepo

class QuestionViewModel(application: Application) : AndroidViewModel(application) {
    var repo = QuestionRepo.instance

    
}