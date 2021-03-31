package com.jap.cloudinteractiveFrank.ui.detail;

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.jap.cloudinteractiveFrank.R
import com.jap.cloudinteractiveFrank.databinding.DetailFragmentBinding
import com.jap.cloudinteractiveFrank.databinding.PhotosFragmentBinding
import com.jap.cloudinteractiveFrank.ui.photos.PhotosFragmentArgs


class DetailFragment : Fragment(){

    private lateinit var detailFragmentBinding: DetailFragmentBinding
    private lateinit var detailViewModel: DetailViewModel


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        detailFragmentBinding = DetailFragmentBinding.inflate(inflater, container, false)
        detailViewModel = ViewModelProvider(this,
            DetailViewModelFactory(requireContext())
        ).get(DetailViewModel::class.java)

        val args: PhotosFragmentArgs by navArgs()
        detailFragmentBinding.textViewDetailId.text = getString(R.string.id,args.id)
        detailFragmentBinding.textViewDetailTitle.text = getString(R.string.title,args.title)
        detailFragmentBinding.imageViewDetail.setImageDrawable(resources.getDrawable(R.drawable.ic_refresh,null))

        detailViewModel.detailPhoto.observe(viewLifecycleOwner, Observer {
//            detailFragmentBinding.imageViewDetail.layoutParams.height = requireActivity().windowManager.defaultDisplay.width
            detailFragmentBinding.imageViewDetail.setImageBitmap(it)
        })
        detailFragmentBinding.imageViewDetail.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.back_photos)
        }
        detailFragmentBinding.backMainButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.back_photos)
        }

        detailViewModel.getUrlBitmap(args.url)
        return detailFragmentBinding.root
    }

}


