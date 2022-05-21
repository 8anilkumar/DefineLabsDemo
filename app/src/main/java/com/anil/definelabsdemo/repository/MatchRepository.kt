package com.anil.definelabsdemo.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.anil.definelabsdemo.models.AllMatchedResponse
import com.anil.definelabsdemo.utils.ApiInterface
import com.anil.definelabsdemo.utils.Contants
import com.anil.definelabsdemo.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchRepository(val context: Context?){

    fun getAllMatchedData(): MutableLiveData<AllMatchedResponse?> {

        val responseData: MutableLiveData<AllMatchedResponse?> = MutableLiveData<AllMatchedResponse?>()
        val apiService: ApiInterface? = RetrofitClient.getClient(Contants.BASE_URL)?.create(ApiInterface::class.java)
        val call: Call<AllMatchedResponse?>? = apiService?.getAllMatched()
        call?.enqueue(object : Callback<AllMatchedResponse?> {
            override fun onResponse(call: Call<AllMatchedResponse?>, response: Response<AllMatchedResponse?>) {
                Log.v("viewModel", response.toString())
                if (response.code() == 200) {
                    responseData.value = response.body()
                } else if (response.code() == 422 || response.code() == 500) {
                   // errorMessage.postValue(response.message())
                    Toast.makeText(context,""+response.message(),Toast.LENGTH_SHORT).show()
                } else {
                   // errorMessage.postValue("Something Went Wrong")
                    Toast.makeText(context,"Something went wrong!",Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<AllMatchedResponse?>, throwable: Throwable) {
                Log.e("viewModel", throwable.toString())
            }
        })

        return responseData
    }
}