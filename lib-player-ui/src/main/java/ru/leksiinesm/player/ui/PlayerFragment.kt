package ru.leksiinesm.player.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.lib_player_ui.R
import com.example.lib_player_ui.databinding.PlayerFragmentBinding
import ru.leksiinesm.player.domain.PlayerInteractor
import ru.leksiinesm.player.domain.PlayerInteractorImpl
import ru.leksiinesm.player.presentation.PlayerViewModel
import ru.leksiinesm.player.presentation.factory.PlayerViewModelFactory
import ru.leksiinesm.player.ui.widget.PlayButton

// TODO draft
class PlayerFragment : Fragment() {

    private lateinit var interactor: PlayerInteractor
    private lateinit var playerViewModel: PlayerViewModel

    private lateinit var playButton: PlayButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        interactor = PlayerInteractorImpl(context.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerViewModel = activity?.run {
            ViewModelProviders.of(this, PlayerViewModelFactory(interactor))[PlayerViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: PlayerFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.player_fragment, container, false)
        binding.viewModel = playerViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        playButton = view.findViewById(R.id.play_button)
        /*if (playerViewModel.isPlaying()) {
            playButton.showStopIcon()
        } else {
            playButton.showPlayIcon()
        }*/
    }
}