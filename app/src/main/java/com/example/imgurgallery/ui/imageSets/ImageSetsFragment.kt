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

    private val linearDecoration: RecyclerView.ItemDecoration by lazy {
        VerticalItemDecoration(
                resources.getDimension(R.dimen.margin_normal).toInt())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ImageSetsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        linearLayoutManager = LinearLayoutManager(activity)
        setLayoutManager()
        binding.imageList.adapter = adapter

        adapter.submitList(args.data.toList())
    }

    private fun setLayoutManager() {
        val recyclerView = binding.imageList

        var scrollPosition = 0
        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
        }

        recyclerView.addItemDecoration(linearDecoration)
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.scrollToPosition(scrollPosition)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val SPAN_COUNT = 3
        fun newInstance() = ImageSetsFragment()
    }
}