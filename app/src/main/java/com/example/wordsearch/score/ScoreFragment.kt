package com.example.wordsearch.score

import android.content.Intent
import android.os.Bundle
import android.view.*
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
 * Fragment where the final score is shown, after the game is over.
 * Allows you to share your score with friends!
 */
class ScoreFragment : Fragment() {

    private lateinit var binding: ScoreFragmentBinding
    private lateinit var viewModelFactory: ScoreViewModelFactory
    private lateinit var viewModel: ScoreViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("Score screen is displayed.")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.score_fragment, container, false
        )

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

        /**
         * Enables share menu.
         */
        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * Inflate share menu.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.share_menu, menu)
    }

    /**
     * Creates sharing intent.
     */
    private fun getShareIntent(): Intent {
        val args = arguments?.let { ScoreFragmentArgs.fromBundle(it) }
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.share_message, args?.score)
            )
        return shareIntent
    }

    /**
     * Calls the startActivity method with the intent, which starts the new activity with our intent.
     */
    private fun shareScore() {
        startActivity(getShareIntent())
    }

    /**
     * Connects intent with menu action.
     * Navigates to another app as specified by the intent.
     * Call shareScore method only when the Id matches that defined in the share_menu.xml file.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.share -> shareScore()
        }
        return super.onOptionsItemSelected(item)
    }
}
