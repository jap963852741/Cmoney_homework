package com.jap.cloudinteractiveFrank.ui.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.cloudinteractiveFrank.R
import com.jap.cloudinteractiveFrank.databinding.PhotosFragmentBinding


class PhotosFragment : Fragment(){

    private lateinit var photosFragmentBinding: PhotosFragmentBinding
    private lateinit var photosViewModel: PhotosViewModel
    private lateinit var photosAdapter: PhotosAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        photosViewModel = ViewModelProvider(this,
            PhotosViewModelFactory()
        ).get(PhotosViewModel::class.java)
        photosFragmentBinding = PhotosFragmentBinding.inflate(inflater, container, false)



        photosFragmentBinding.backMainButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.back_main)
        }
        photosFragmentBinding.loading.visibility = View.VISIBLE
        photosViewModel.photosResult.observe(viewLifecycleOwner, Observer {
            photosFragmentBinding.loading.visibility = View.GONE
            if(it.success != null) {
                photosAdapter = PhotosAdapter(it.success.results, container!!)
                photosFragmentBinding.reViewPhoto.layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
                )
                photosFragmentBinding.reViewPhoto.adapter = photosAdapter
           }else{
               Toast.makeText(
                   context,
                   "${it.error} ",
                   Toast.LENGTH_LONG
               ).show()
           }

        })

        photosViewModel.Get_api()

        return photosFragmentBinding.root
    }


    override fun onDestroyView() {
        photosFragmentBinding.reViewPhoto.adapter = null    //解決內存洩漏
        super.onDestroyView()
    }
}


