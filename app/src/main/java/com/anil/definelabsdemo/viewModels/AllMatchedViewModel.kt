package com.anil.definelabsdemo.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anil.definelabsdemo.models.Venue
import com.anil.definelabsdemo.models.VenueResponse
import com.anil.definelabsdemo.repository.MatchRepository

class AllMatchedViewModel (application: Application?) : AndroidViewModel(application!!) {

    private var repository: MatchRepository = MatchRepository(application)

     var venueList: MutableLiveData<VenueResponse> = MutableLiveData<VenueResponse>()

    fun callApiToGetVenues() {
        repository.getVenueList()?.let { venues->

            venueList = venues
        }
    }

    fun getVenues(): MutableLiveData<VenueResponse> {
        return venueList
    }
}