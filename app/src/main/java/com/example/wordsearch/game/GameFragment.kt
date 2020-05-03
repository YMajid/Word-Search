package com.example.wordsearch.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.wordsearch.R
import com.example.wordsearch.databinding.GameFragmentBinding
import timber.log.Timber

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("Game has started.")
        binding = DataBindingUtil.inflate(inflater,
            R.layout.game_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel

        binding.lifecycleOwner = this
        //Replacing what's in the View with the newest grid & giving used words list to the view
        binding.letterGrid.data = viewModel.grid
        binding.letterGrid.usedWordsList = viewModel.usedWordsList

        viewModel.eventGameFinished.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished) {
                Timber.i("Game has finished.")
                val currentScore = binding.letterGrid.score.value ?: 0
                val action = GameFragmentDirections.actionGameFragmentToScoreFragment(currentScore)
                findNavController().navigate(action)
                viewModel.onGameFinishComplete()
            }
        })

        return binding.root
    }
}