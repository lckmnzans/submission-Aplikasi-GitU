package com.dicoding.gitu.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.gitu.GithubResponse
import com.dicoding.gitu.Items
import com.dicoding.gitu.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _listUser = MutableLiveData<List<Items>>()
    val listUser: LiveData<List<Items>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
        var QUERY = "fadhil"
    }

    private fun findUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(QUERY)
        //menerapkan kembalian dari Http Request ke dalam Callback dengan tipe parameter GithubResponse
        client.enqueue(object: Callback<GithubResponse> {
            //jika didapatkan response
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    //mendapatkan response sukses dari Http Request
                    val responseBody = response.body()
                    if (responseBody?.items != null) {
                        //jika body response tidak null, diambil data dari key items dan totalCount
                        _listUser.value = responseBody.items
                    }
                } else {
                    //medapatkan response tidak sukses dari Http Request yg dimunculkan di logcat
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            //jika tidak didapatkan respon
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun updateQuery(query: String) {
        QUERY = query
        findUser()
    }

    init {
        findUser()
    }
}