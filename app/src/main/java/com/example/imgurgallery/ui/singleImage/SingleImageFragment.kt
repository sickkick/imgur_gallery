package com.example.imgurgallery.ui.singleImage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.imgurgallery.R
import com.example.imgurgallery.databinding.SearchFragmentBinding
import com.example.imgurgallery.databinding.SingleImageFragmentBinding
import com.example.imgurgallery.di.Injectable
import com.example.imgurgallery.ui.imageSets.ImageSetsFragmentArgs
import com.example.imgurgallery.ui.search.SearchViewModel
import javax.inject.Inject

class SingleImageFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    val viewModel: SingleImageViewModel by viewModels {
        viewModelFactory
    }

    private var _binding: SingleImageFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: SingleImageFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = SingleImageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.item = args.imageData
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}