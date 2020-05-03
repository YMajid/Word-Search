package com.example.wordsearch.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.wordsearch.R
import com.example.wordsearch.databinding.ScoreFragmentBinding
import timber.log.Timber

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {

    private lateinit var binding: ScoreFragmentBinding
    private lateinit var viewModelFactory: ScoreViewModelFactory
    private lateinit var viewModel: ScoreViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("Score screen is displayed.")
        binding = DataBindingUtil.inflate(inflater,
            R.layout.score_fragment, container, false)

        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()

        viewModelFactory =
            ScoreViewModelFactory(scoreFragmentArgs.score)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScoreViewModel::class.java)

        binding.scoreViewModel = viewModel

        viewModel.playAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain) {
                Timber.i("Game being played again.")
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
            }
        })

        return binding.root
    }

}
