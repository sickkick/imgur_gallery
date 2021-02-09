package com.example.imgurgallery.ui.imageSets

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imgurgallery.R
import com.example.imgurgallery.databinding.ImageSetsFragmentBinding
import com.example.imgurgallery.databinding.SearchFragmentBinding
import com.example.imgurgallery.di.Injectable
import com.example.imgurgallery.helpers.GridSpacingItemDecoration
import com.example.imgurgallery.helpers.VerticalItemDecoration
import com.example.imgurgallery.ui.search.SearchAdapter
import com.example.imgurgallery.ui.search.SearchFragment
import com.example.imgurgallery.ui.search.SearchViewModel
import javax.inject.Inject

class ImageSetsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    val viewModel: ImageSetsViewModel by viewModels {
        viewModelFactory
    }

    private var _binding: ImageSetsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: ImageSetsFragmentArgs by navArgs()

    private val adapter: ImageAdapter by lazy { ImageAdapter() }
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var gridLayoutManager: GridLayoutManager
    private val linearDecoration: RecyclerView.ItemDecoration by lazy {
        VerticalItemDecoration(
                resources.getDimension(R.dimen.margin_normal).toInt())
    }
    private val gridDecoration: RecyclerView.ItemDecoration by lazy {
        GridSpacingItemDecoration(
                SearchFragment.SPAN_COUNT, resources.getDimension(R.dimen.margin_grid).toInt())
    }

    private var isLinearLayoutManager: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ImageSetsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        linearLayoutManager = LinearLayoutManager(activity)
        gridLayoutManager = GridLayoutManager(activity, SearchFragment.SPAN_COUNT)
        setLayoutManager()
        binding.imageList.adapter = adapter

        adapter.submitList(args.data.toList())
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
        val recyclerView = binding.imageList

        var scrollPosition = 0
        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
        }

        recyclerView.removeItemDecoration(gridDecoration)
        recyclerView.addItemDecoration(linearDecoration)
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.scrollToPosition(scrollPosition)
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
        fun newInstance() = ImageSetsFragment()
    }
}