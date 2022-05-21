package com.anil.definelabsdemo.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anil.definelabsdemo.models.AllMatchedResponse
import com.anil.definelabsdemo.repository.MatchRepository

class AllMatchedViewModel (application: Application?) : AndroidViewModel(application!!) {

    private var repository: MatchRepository = MatchRepository(application)

    lateinit var allMatchedResponse: MutableLiveData<AllMatchedResponse?>

    fun callApiForGetAllMatchedData() {
        allMatchedResponse = repository.getAllMatchedData()
    }

    fun getAllMatchedData(): MutableLiveData<AllMatchedResponse?> {
        return allMatchedResponse
    }
}