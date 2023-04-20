package com.dicoding.gitu.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.gitu.Items
import com.dicoding.gitu.UserDetailResponse
import com.dicoding.gitu.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userDetail = MutableLiveData<UserDetailResponse>()
    val userDetail: LiveData<UserDetailResponse> = _userDetail

    private val _userFollowsList = MutableLiveData<List<Items>>()
    val userFollowsList: LiveData<List<Items>> = _userFollowsList

    companion object {
        private const val TAG = "DetailActivity"
        var USERNAME = "username"
    }

    private fun getUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        //menerapkan kembalian dari Http Request ke dalam Callback dengan tipe parameter UserResponse
        client.enqueue(object: Callback<UserDetailResponse>{
            //jika didapatkan response
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    //mendapatkan response sukses dari Http Request
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userDetail.value = responseBody
                    } else {
                        //medapatkan response tidak sukses dari Http Request yg dimunculkan di logcat
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }
            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUserFollowsDetail(client: Call<List<Items>>) {
        //menerapkan kembalian dari Http Request ke dalam Callback dengan tipe parameter GithubResponse
        client.enqueue(object: Callback<List<Items>> {
            //jika didapatkan response
            override fun onResponse(call: Call<List<Items>>, response: Response<List<Items>>) {
                if (response.isSuccessful) {
                    //mendapatkan response sukses dari Http Request
                    val responseBody = response.body()
                    if (responseBody != null) {
                        //jika body response tidak null, diambil data
                        _userFollowsList.value = responseBody
                    }
                } else {
                    //medapatkan response tidak sukses dari Http Request yg dimunculkan di logcat
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            //jika tidak didapatkan respon
            override fun onFailure(call: Call<List<Items>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    init {
        getUserDetail(USERNAME)
    }
}