package com.example.imgurgallery.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imgurgallery.R
import com.example.imgurgallery.data.api.Result
import com.example.imgurgallery.databinding.SearchFragmentBinding
import com.example.imgurgallery.di.Injectable
import com.example.imgurgallery.helpers.GridSpacingItemDecoration
import com.example.imgurgallery.helpers.VerticalItemDecoration
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    val viewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter: SearchAdapter by lazy { SearchAdapter() }
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var gridLayoutManager: GridLayoutManager
    private val linearDecoration: RecyclerView.ItemDecoration by lazy {
        VerticalItemDecoration(
                resources.getDimension(R.dimen.margin_normal).toInt())
    }
    private val gridDecoration: RecyclerView.ItemDecoration by lazy {
        GridSpacingItemDecoration(
                SPAN_COUNT, resources.getDimension(R.dimen.margin_grid).toInt())
    }

    private var isLinearLayoutManager: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.searchReults.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Result.Status.SUCCESS -> {
                    adapter.submitList(it.data?.data)
                    binding.loadingCV.visibility = View.GONE
                }
                Result.Status.LOADING -> {
                    binding.loadingCV.visibility = View.VISIBLE
                }
                Result.Status.ERROR -> {
                    binding.loadingCV.visibility = View.GONE
                    Snackbar.make(binding.root, it.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        binding.loadingCV.visibility = View.GONE
        linearLayoutManager = LinearLayoutManager(activity)
        gridLayoutManager = GridLayoutManager(activity, SPAN_COUNT)
        setLayoutManager()
        binding.albumList.adapter = adapter

        setupSearch()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list_representation, menu)
        setDataRepresentationIcon(menu.findItem(R.id.list))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.list -> {
                isLinearLayoutManager = !isLinearLayoutManager
                setDataRepresentationIcon(item)
                setLayoutManager()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setLayoutManager() {
        val recyclerView = binding.albumList

        var scrollPosition = 0
        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
        }

        if (isLinearLayoutManager) {
            recyclerView.removeItemDecoration(gridDecoration)
            recyclerView.addItemDecoration(linearDecoration)
            recyclerView.layoutManager = linearLayoutManager
        } else {
            recyclerView.removeItemDecoration(linearDecoration)
            recyclerView.addItemDecoration(gridDecoration)
            recyclerView.layoutManager = gridLayoutManager
        }

        recyclerView.scrollToPosition(scrollPosition)
    }

    private fun setupSearch() {
        val searchBox: SearchView = binding.searchView

        searchBox.setOnQueryTextListener(null)

        searchBox.setIconifiedByDefault(false)
        searchBox.queryHint = "Search for an album"
        searchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!!.isNotEmpty()) {
                    viewModel.getSearchResults(query)
                    searchBox.clearFocus()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setDataRepresentationIcon(item: MenuItem) {
        item.setIcon(if (isLinearLayoutManager)
            R.drawable.ic_list_white_24dp else R.drawable.ic_grid_list_24dp)
    }

    companion object {
        const val SPAN_COUNT = 3
        fun newInstance() = SearchFragment()
    }
}