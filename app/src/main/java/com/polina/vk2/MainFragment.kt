package com.polina.vk2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.polina.vk2.databinding.MainFragmentBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Response

class MainFragment : Fragment(R.layout.main_fragment) {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentUrl: String
    private val key = "live_ssyALQBcQkji6KfNgOluG0zk4o8vEKWKsKQrvE8NHluVE5rd2yMBWM5XlqbY4d9H"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.INVISIBLE
        if (savedInstanceState != null) {
            val url = savedInstanceState.getString("url")
            Glide.with(this)
                .load(url)
                .into(binding.loadedImage)
        }
        binding.loadButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                loadData()
            }
        }
    }

    private suspend fun loadData() {
        binding.progressBar.visibility = View.VISIBLE
        try {
            val response = RetrofitInstance().api.getImages(key)
            if (response.isSuccessful) {
                val catApiResponse = response.body()
                if (catApiResponse != null) {
                    val imageUrl = catApiResponse[0].url
                    currentUrl = imageUrl
                    Glide.with(this)
                        .load(imageUrl)
                        .into(binding.loadedImage)
                }
            }
        } catch (e: Exception) {
            binding.loadButton.text = getString(R.string.error)
        } finally {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("url", currentUrl)
    }
}
