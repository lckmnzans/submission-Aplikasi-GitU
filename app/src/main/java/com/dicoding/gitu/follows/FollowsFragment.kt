package com.dicoding.gitu.follows

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.gitu.GithubResponse
import com.dicoding.gitu.Items
import com.dicoding.gitu.api.ApiConfig
import com.dicoding.gitu.databinding.FragmentFollowsBinding
import com.dicoding.gitu.user.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowsFragment : Fragment() {
    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        private const val TAG = "FollowsFragment"
    }
    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    /*
    private fun getUsers(login: String) {
        val client = ApiConfig.getApiService().getFollowers(login)
        //menerapkan kembalian dari Http Request ke dalam Callback dengan tipe parameter GithubResponse
        client.enqueue(object: Callback<GithubResponse> {
            //jika didapatkan response
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                if (response.isSuccessful) {
                    //mendapatkan response sukses dari Http Request
                    val responseBody = response.body()
                    if (responseBody != null) {
                        //jika body response tidak null, diambil data dari key items dan totalCount
                        setUsers(responseBody.items)
                    }
                } else {
                    //medapatkan response tidak sukses dari Http Request yg dimunculkan di logcat
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            //jika tidak didapatkan respon
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    private fun setUsers(users: List<Items>) {
        //membuat list kosong dengan tipe parameter User -> dari kelas data User
        val list = ArrayList<User>()
        for (user in users) {
            //untuk tiap-tiap user, data-datanya dimasukkan ke dalam kelas data User() yang akan dikirimkan ke adapter
            val userData = User(user.avatarUrl.toString(), user.login.toString())
            //menambahkan data tersebut ke dalam list
            list.add(userData)
        }
        val listUser = UserFollowsAdapter(list)
        binding.rvFollowsList.adapter = listUser
    }
    */
}