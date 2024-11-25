package com.polina.vk2.fragments

import android.media.VolumeShaper.Configuration
import com.polina.vk2.recycler.MyAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.polina.vk2.R
import com.polina.vk2.databinding.MainFragmentBinding
import com.polina.vk2.network.RetrofitInstance
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.main_fragment) {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var currentUrlList = mutableListOf<String>()
    private val adapter = MyAdapter()
    private val url = "url"
    private var isLoading = false
    private var spanCount = 2


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
            val savedUrls = savedInstanceState.getStringArrayList(url)
            if (savedUrls != null) {
                currentUrlList = savedUrls.toMutableList()
                adapter.setItems(currentUrlList)
            }
        }
        setUpRecycler()
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
            val response = RetrofitInstance().api.getImages()
            if (response.isSuccessful) {
                val catApiResponse = response.body()
                if (catApiResponse != null) {
                    for (i in catApiResponse) {
                        currentUrlList.add(i.url)
                    }
                    adapter.setItems(currentUrlList)
                } else {
                    Snackbar.make(binding.root, getString(R.string.error), Snackbar.LENGTH_SHORT)
                        .show()
                }
            } else {
                Snackbar.make(binding.root, getString(R.string.error), Snackbar.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Snackbar.make(binding.root, getString(R.string.error), Snackbar.LENGTH_SHORT).show()
        } finally {
            binding.progressBar.visibility = View.INVISIBLE
            binding.loadButton.isEnabled = true
        }
    }

    fun setUpRecycler(){
        if (resources.configuration.orientation == 1)
            spanCount = 2
        else
            spanCount = 3
        val layoutManager = GridLayoutManager(context, spanCount)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && !isLoading) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                        isLoading = true
                        binding.progressBar.visibility = View.VISIBLE
                        viewLifecycleOwner.lifecycleScope.launch {
                            loadData()
                            isLoading = false
                        }
                    }
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(url, ArrayList(currentUrlList))
    }
}
