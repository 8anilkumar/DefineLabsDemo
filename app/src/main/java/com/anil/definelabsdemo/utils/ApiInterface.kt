package com.anil.definelabsdemo.utils

import com.anil.definelabsdemo.models.AllMatchedData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("search?ll=40.7484,-73.9857&oauth_token=NPKYZ3WZ1VYMNAZ2FLX1WLECAWSMUVOQZOIDBN53F3LVZBPQ&v=20180616")
    fun getAllMatched(): Call<AllMatchedData?>?


}