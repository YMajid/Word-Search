package com.example.wordsearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.wordsearch.databinding.ScoreFragmentBinding

class ScoreFragment : Fragment() {

    private lateinit var binding: ScoreFragmentBinding
    private lateinit var viewModelFactory: ScoreViewModelFactory
    private lateinit var viewModel: ScoreViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.id.score_layout, container, false)

        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()

        viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.time)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScoreViewModel::class.java)

        binding.scoreViewModel = viewModel

        viewModel.playAgain.observe(this, Observer { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
            }
        })

        return binding.root
    }

}
