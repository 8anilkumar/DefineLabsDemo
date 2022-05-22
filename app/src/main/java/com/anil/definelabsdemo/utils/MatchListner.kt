package com.anil.definelabsdemo.utils

import com.anil.definelabsdemo.models.Venue

interface MatchListner {
    fun matchListener(venueList: Venue)
}