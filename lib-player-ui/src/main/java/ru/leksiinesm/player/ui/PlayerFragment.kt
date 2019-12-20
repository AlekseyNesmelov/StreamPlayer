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
import ru.leksiinesm.player.domain.interactor.PlayerInteractor
import ru.leksiinesm.player.domain.interactor.PlayerInteractorImpl
import ru.leksiinesm.player.presentation.PlayerViewModel
import ru.leksiinesm.player.presentation.factory.PlayerViewModelFactory
import ru.leksiinesm.storage.data.storage.DataStorage
import ru.leksiinesm.storage.data.storage.DataStorageImpl

/**
 * Fragment with play button
 */
class PlayerFragment : Fragment() {

    private lateinit var storage: DataStorage
    private lateinit var interactor: PlayerInteractor
    private lateinit var playerViewModel: PlayerViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        storage = DataStorageImpl(context.applicationContext)
        interactor = PlayerInteractorImpl(context.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerViewModel = activity?.run {
            ViewModelProviders.of(
                this,
                PlayerViewModelFactory(interactor, storage)
            )[PlayerViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: PlayerFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.player_fragment, container, false)
        binding.viewModel = playerViewModel
        binding.lifecycleOwner = this@PlayerFragment
        return binding.root
    }
}