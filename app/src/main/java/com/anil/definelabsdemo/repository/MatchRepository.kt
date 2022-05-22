package com.anil.definelabsdemo.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.anil.definelabsdemo.models.AllMatchedData
import com.anil.definelabsdemo.models.Venue
import com.anil.definelabsdemo.models.VenueResponse
import com.anil.definelabsdemo.utils.ApiInterface
import com.anil.definelabsdemo.utils.Constants
import com.anil.definelabsdemo.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchRepository(val context: Context?){

    fun getVenueList(): MutableLiveData<VenueResponse>? {

        val venueList: MutableLiveData<VenueResponse>? = MutableLiveData<VenueResponse>()

        val apiService: ApiInterface? = RetrofitClient.getClient(Constants.BASE_URL)?.create(ApiInterface::class.java)
        val call: Call<AllMatchedData?>? = apiService?.getAllMatched()
        call?.enqueue(object : Callback<AllMatchedData?> {
            override fun onResponse(call: Call<AllMatchedData?>, response: Response<AllMatchedData?>) {
                Log.v("viewModel", response.toString())
                if (response.code() == 200) {

                    venueList?.value = response.body()?.response
                } else if (response.code() == 422 || response.code() == 500) {
                    Toast.makeText(context,""+response.message(),Toast.LENGTH_SHORT).show()
                } else {
                   // errorMessage.postValue("Something Went Wrong")
                    Toast.makeText(context,"Something went wrong!",Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<AllMatchedData?>, throwable: Throwable) {
                Log.e("viewModel", throwable.toString())
            }
        })

        return venueList
    }
}