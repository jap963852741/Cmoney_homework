package com.jap.cloudinteractiveFrank.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.jap.cloudinteractiveFrank.R
import com.jap.cloudinteractiveFrank.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var mainFragmentBinding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        mainFragmentBinding = MainFragmentBinding.inflate(inflater, container, false)
        mainFragmentBinding.requestButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.go_photos)
        }

        return mainFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}