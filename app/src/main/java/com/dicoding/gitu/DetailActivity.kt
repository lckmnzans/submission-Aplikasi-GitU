package com.dicoding.gitu

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.dicoding.gitu.api.ApiConfig
import com.dicoding.gitu.databinding.ActivityDetailBinding
import com.dicoding.gitu.user.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    //menerapkan viewBinding DetailActivity
    private lateinit var activityDetailBinding: ActivityDetailBinding
    //melakukan instansi objek internal dengan companion
    companion object {
        private const val TAG = "DetailActivity"
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        //mengambil data yg dikirim dengan intent dari activity sebelumnya
        val user =if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_USER, User::class.java)
        } else {
            @Suppress("DEPRECATED")
            intent.getParcelableExtra(EXTRA_USER)
        }

        supportActionBar!!.hide()

        //menerapkan data tersebut ke dalam context
        if (user != null) {
            getUserDetail(user.username, user.photo)
        }
    }

    private fun getUserDetail(login: String, avtUrl: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getDetailUser(login)
        //menerapkan kembalian dari Http Request ke dalam Callback dengan tipe parameter UserResponse
        client.enqueue(object: Callback<UserDetailResponse>{
            //jika didapatkan response
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    //mendapatkan response sukses dari Http Request
                    val responseBody = response.body()
                    if (responseBody != null) {
                        //jika body response tidak null
                        val name = responseBody.name
                        val uname = responseBody.login
                        val followers = responseBody.followers
                        val following = responseBody.following
                        //menerapkan imageUrl ke dalam imgView dengan Glide
                        Glide.with(this@DetailActivity).load(avtUrl).into(activityDetailBinding.imgUserProfile)
                        //menerapkan nama dari response ke dalam textView nama
                        if (name != null) {
                            activityDetailBinding.tvUserName.text = name.toString()
                        } else {
                            activityDetailBinding.tvUserName.text = uname.toString()
                        }
                        //menerapkan username dari response ke dalam textView username
                        activityDetailBinding.tvUserUsername.text = uname.toString()
                        //menerapkan jml followers dan following ke dalam textView followers serta following
                        activityDetailBinding.tvFollowersCount.text = followers.toString()
                        activityDetailBinding.tvFollowingCount.text = following.toString()
                    } else {
                        //medapatkan response tidak sukses dari Http Request yg dimunculkan di logcat
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }
            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            //jika sedang loading, progress bar muncul dan recyclerview tidak muncul
            activityDetailBinding.progressBar.visibility = View.VISIBLE
        } else {
            //jika sedang tidak loading, progress bar tidak muncul tetapi recyclerview muncul
            activityDetailBinding.progressBar.visibility = View.GONE
        }
    }
}