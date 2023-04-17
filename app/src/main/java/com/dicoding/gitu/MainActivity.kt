package com.dicoding.gitu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.gitu.api.ApiConfig
import com.dicoding.gitu.databinding.ActivityMainBinding
import com.dicoding.gitu.user.User
import com.dicoding.gitu.user.UserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
        private const val QUERY = "fadhil"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        supportActionBar?.title = "Github User"

        activityMainBinding.rvUsers.layoutManager = LinearLayoutManager(this)
        findUser()
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            activityMainBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityMainBinding.progressBar.visibility = View.GONE
        }
    }
    private fun findUser() {
        showLoading(true)
        val client = ApiConfig.getApiService().getUser(QUERY)
        client.enqueue(object: Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUserData(responseBody.items, responseBody.totalCount)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setUserData(users: List<ItemsItem>, count: Int) {
        val list = ArrayList<User>()
        for (user in users) {
            val userData = User(user.avatarUrl.toString(), user.login.toString(), user.followersUrl.toString(), user.followingUrl.toString())
            list.add(userData)
        }
        val listUser = UserAdapter(list)
        activityMainBinding.rvUsers.adapter = listUser
        activityMainBinding.tvItemCount.text = count.toString()
    }
}