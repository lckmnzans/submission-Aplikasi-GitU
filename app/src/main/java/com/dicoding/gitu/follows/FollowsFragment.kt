package com.dicoding.gitu.follows

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.gitu.Items
import com.dicoding.gitu.api.ApiConfig
import com.dicoding.gitu.databinding.FragmentFollowsBinding
import com.dicoding.gitu.user.User
import com.dicoding.gitu.viewModel.DetailViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowsFragment : Fragment() {
    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "section_login"
        private const val TAG = "FollowsFragment"
    }
    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(ARG_POSITION, 0)
        val username = arguments?.getString(ARG_USERNAME).toString()

        binding.rvFollowsList.layoutManager = LinearLayoutManager(requireContext())
        if (position == 1) {
            viewModel.getUserFollowsDetail(ApiConfig.getApiService().getFollowers(username))
        } else {
            viewModel.getUserFollowsDetail(ApiConfig.getApiService().getFollowing(username))
        }
        viewModel.userFollowsList.observe(viewLifecycleOwner) { users -> setUsers(users) }
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
    private fun getUserFollowsDetail(client: Call<List<Items>>) {
        //menerapkan kembalian dari Http Request ke dalam Callback dengan tipe parameter GithubResponse
        client.enqueue(object: Callback<List<Items>> {
            //jika didapatkan response
            override fun onResponse(call: Call<List<Items>>, response: Response<List<Items>>) {
                if (response.isSuccessful) {
                    //mendapatkan response sukses dari Http Request
                    val responseBody = response.body()
                    if (responseBody != null) {
                        //jika body response tidak null, diambil data
                        setUsers(responseBody)
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
    */

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
}