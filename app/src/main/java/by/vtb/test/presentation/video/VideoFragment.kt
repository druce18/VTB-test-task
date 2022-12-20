package by.vtb.test.presentation.video

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.vtb.test.databinding.FragmentVideoBinding
import by.vtb.test.extention.appComponent
import by.vtb.test.extention.setGone
import by.vtb.test.extention.setVisible
import by.vtb.test.extention.showSnackbarErrorIndefinite
import by.vtb.test.presentation.base.BaseFragment
import by.vtb.test.presentation.base.UiState
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem


class VideoFragment : BaseFragment<FragmentVideoBinding>() {

    private val viewModel: VideoViewModel by viewModels()
    private var player: ExoPlayer? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVideoBinding {
        return FragmentVideoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePlayer()
        lifecycleScope.launchWhenResumed {
            viewModel.uiState.collect { uiState ->
                showState(uiState)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player = null
    }

    private fun showState(uiState: UiState<String>) {
        when (uiState) {
            is UiState.Success -> {
                setVideo(uiState)
            }
            is UiState.Loading -> {
                binding.progressBar.setVisible()
            }
            is UiState.Error -> {
                showError(uiState)
            }
        }
    }

    private fun setVideo(uiState: UiState.Success<String>) {
        with(binding) {
            val mediaItem = MediaItem.fromUri(uiState.data)
            player?.addMediaItem(mediaItem)
            progressBar.setGone()
        }
    }

    private fun showError(uiState: UiState.Error) {
        with(binding) {
            videoFragment.showSnackbarErrorIndefinite(uiState.message)
            progressBar.setGone()
        }
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer
            }
    }

    companion object {

        fun newInstance(link: String) = VideoFragment().apply {
            arguments = Bundle().apply {
                putString(VideoViewModel.ARG_LINK, link)
            }
        }
    }
}
