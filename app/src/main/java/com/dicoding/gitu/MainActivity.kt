package com.dicoding.gitu

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.gitu.databinding.ActivityMainBinding
import com.dicoding.gitu.user.User
import com.dicoding.gitu.user.UserAdapter
import com.dicoding.gitu.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
       ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        supportActionBar?.title = "Github User"

        //menerapkan recyclerV dalam linear vertikal
        activityMainBinding.rvUsers.layoutManager = LinearLayoutManager(this)
        viewModel.listUser.observe(this, { items -> setUserData(items) })
        viewModel.isLoading.observe(this, { showLoading(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_search, menu)

        //mulai implementasi search bar
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                viewModel.updateQuery(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        //akhir implementasi seach bar
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            //jika sedang loading, progress bar muncul dan recyclerview tidak muncul
            activityMainBinding.progressBar.visibility = View.VISIBLE
            activityMainBinding.rvUsers.visibility = View.GONE
        } else {
            //jika sedang tidak loading, progress bar tidak muncul tetapi recyclerview muncul
            activityMainBinding.progressBar.visibility = View.GONE
            activityMainBinding.rvUsers.visibility = View.VISIBLE
        }
    }

    private fun setUserData(users: List<Items>) {
        //membuat list kosong dengan tipe parameter User -> dari kelas data User
        val list = ArrayList<User>()
        for (user in users) {
            //untuk tiap-tiap user, data-datanya dimasukkan ke dalam kelas data User() yang akan dikirimkan ke adapter
            val userData = User(user.avatarUrl.toString(), user.login.toString())
            //menambahkan data tersebut ke dalam list
            list.add(userData)
        }
        //menerapkan list ke dalam UserAdapter
        val listUser = UserAdapter(list)
        //menerapkan adapter tersebut ke adapter dlm MainActivity
        activityMainBinding.rvUsers.adapter = listUser

        //menerapkan listener pada tiap-tiap item di listUser
        //ketika item dipilih, akan muncul event callback yang memanggil activity tujuan dg ikut mengirimkan data
        listUser.setOnUserClickCallback(object: UserAdapter.OnUserClickCallback {
            //melakukan override method di interface OnUserClickCallback
            override fun onUserClicked(data: User) {
                val userDetail = User(data.photo, data.username)
                //menginstansi intent untuk mengirim data dari MainActivity ke DetailActivity
                val toDetail = Intent(this@MainActivity, DetailActivity::class.java)
                //meletakkan data ke dalam intent
                toDetail.putExtra(DetailActivity.EXTRA_USER, userDetail)
                //memulai intent eksplisit
                startActivity(toDetail)
            }
        })
    }
}