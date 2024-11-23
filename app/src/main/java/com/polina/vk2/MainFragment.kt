package com.polina.vk2

import MyAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.polina.vk2.databinding.MainFragmentBinding
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.main_fragment) {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var currentUrlList = mutableListOf<String>()
    private val adapter = MyAdapter()
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
        binding.recyclerView.adapter = adapter
        if (savedInstanceState != null) {
            val savedUrls = savedInstanceState.getStringArrayList("url")
            if (savedUrls != null) {
                currentUrlList = savedUrls.toMutableList()
                adapter.setItems(currentUrlList)
            }
        }
        binding.loadButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                loadData()
            }
        }
    }

    private suspend fun loadData() {
        binding.loadButton.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE
        try {
            val response = RetrofitInstance().api.getImages(key)
            if (response.isSuccessful) {
                val catApiResponse = response.body()
                if (catApiResponse != null) {
                    val imageUrl = catApiResponse[0].url
                    currentUrlList.add(imageUrl)
                    adapter.addItems(imageUrl)
                }
            }
        } catch (e: Exception) {
            Snackbar.make(binding.root, getString(R.string.error), Snackbar.LENGTH_SHORT).show()
        } finally {
            binding.progressBar.visibility = View.INVISIBLE
            binding.loadButton.isEnabled = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("url", ArrayList(currentUrlList))
    }
}
