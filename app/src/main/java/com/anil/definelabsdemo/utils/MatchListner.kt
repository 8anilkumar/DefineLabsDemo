package com.anil.definelabsdemo.utils

import com.anil.definelabsdemo.models.AllMatchedResponse
import com.anil.definelabsdemo.models.MatchResponse
import com.anil.definelabsdemo.models.Venue

interface MatchListner {
    fun matchListener(venueList: AllMatchedResponse)
}