package com.example.wordsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.wordsearch.databinding.TitleFragmentBinding

class TitleFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: TitleFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.title_fragment, container, false)

//        binding.playGameButton.setOnClickListener {
//
//        }

        return binding.root
    }
}