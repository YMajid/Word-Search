package com.example.wordsearch.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordsearch.R
import com.example.wordsearch.databinding.TitleFragmentBinding
import timber.log.Timber

/**
 * Fragment for the starting or title screen of the app.
 */
class TitleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: TitleFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.title_fragment, container, false)

        binding.playGameButton.setOnClickListener {
            Timber.i("Play button clicked.")
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }

        return binding.root
    }
}