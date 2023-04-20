package com.dicoding.gitu

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.gitu.api.ApiConfig
import com.dicoding.gitu.databinding.ActivityDetailBinding
import com.dicoding.gitu.follows.SectionsPageAdapter
import com.dicoding.gitu.user.User
import com.dicoding.gitu.viewModel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    //menerapkan viewBinding DetailActivity
    private lateinit var activityDetailBinding: ActivityDetailBinding
    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
    }
    //melakukan instansi objek internal dengan companion
    companion object {
        const val EXTRA_USER = "extra_user"
        var EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        //mengambil data yg dikirim dengan intent dari activity sebelumnya
        val user = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_USER, User::class.java)
        } else {
            @Suppress("DEPRECATED")
            intent.getParcelableExtra(EXTRA_USER)
        }

        supportActionBar!!.hide()

        //menerapkan data tersebut ke dalam context
        if (user != null) {
            EXTRA_USERNAME = user.username
            DetailViewModel.USERNAME = user.username
            viewModel.userDetail.observe(this, { userDetail -> setUserDetail(userDetail)})
            viewModel.isLoading.observe(this, { showLoading(it) })
        }

        val sectionsPageAdapter = SectionsPageAdapter(this)
        val viewPager: ViewPager2 = activityDetailBinding.viewPager
        viewPager.adapter = sectionsPageAdapter
        val tabs: TabLayout = activityDetailBinding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
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

    private fun setUserDetail(user: UserDetailResponse) {
        //jika body response tidak null
        val name = user.name
        val uname = user.login
        val followers = user.followers
        val following = user.following
        //menerapkan imageUrl ke dalam imgView dengan Glide
        Glide.with(this@DetailActivity).load(user.avatarUrl).into(activityDetailBinding.imgUserProfile)
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
    }
}