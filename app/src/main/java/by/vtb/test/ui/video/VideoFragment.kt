package by.vtb.test.ui.video

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.vtb.test.R
import by.vtb.test.databinding.FragmentVideoBinding
import by.vtb.test.extention.appComponent
import by.vtb.test.extention.setGone
import by.vtb.test.extention.setVisible
import by.vtb.test.extention.showSnackbarErrorIndefinite
import by.vtb.test.ui.base.BaseFragment
import by.vtb.test.ui.base.UiState
import kotlinx.coroutines.flow.collect

class VideoFragment : BaseFragment() {

    private val viewModel: VideoViewModel by viewModels()
    private var _binding: FragmentVideoBinding? = null
    private val binding: FragmentVideoBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingVideoView()
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                showState(uiState)
            }
        }
    }

    private fun setUri(uiState: UiState.Success<String>) {
        with(binding) {
            progressBar.setGone()
            val uri = Uri.parse(uiState.data)
            videoView.setVideoURI(uri)
        }
    }

    private fun showState(uiState: UiState<String>) {
        when (uiState) {
            is UiState.Success -> {
                setUri(uiState)
            }
            is UiState.Loading -> {
                binding.progressBar.setVisible()
            }
            is UiState.Error -> {
                showError(uiState)
            }
        }
    }

    private fun showError(uiState: UiState.Error) {
        with(binding) {
            progressBar.setGone()
            videoFragment.showSnackbarErrorIndefinite(uiState.message, R.string.close)
        }
    }

    private fun settingVideoView() {
        with(binding) {
            videoView.setZOrderOnTop(true)
            val mediaController = MediaController(requireContext())
            videoView.setMediaController(mediaController)
            videoView.setOnPreparedListener {
                videoView.seekTo(PREVIEW_VIDEO_MS)
                mediaController.show(TIMEOUT_SHOW_MS)
            }
            videoView.setOnCompletionListener {
                mediaController.show(TIMEOUT_SHOW_MS)
            }
            videoView.setOnErrorListener { _, _, _ ->
                videoView.showSnackbarErrorIndefinite(getString(R.string.error_loading), R.string.close)
                true
            }
            videoFragment.setOnClickListener {
                mediaController.show()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val TIMEOUT_SHOW_MS = 60000
        private const val PREVIEW_VIDEO_MS = 1

        fun newInstance(link: String) =
            VideoFragment().apply {
                arguments = Bundle().apply {
                    putString(VideoViewModel.ARG_LINK, link)
                }
            }
    }
}
