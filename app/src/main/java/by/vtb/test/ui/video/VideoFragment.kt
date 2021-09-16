package by.vtb.test.ui.video

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import by.vtb.test.R
import by.vtb.test.databinding.FragmentVideoBinding
import by.vtb.test.extention.showSnackbarLong

class VideoFragment : Fragment() {

    private var _binding: FragmentVideoBinding? = null
    private val binding: FragmentVideoBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { arg ->
            val link = arg.getString(ARG_LINK)
            settingVideoView(link)
        }
    }

    private fun settingVideoView(link: String?) {
        with(binding) {
            videoView.setVideoURI(Uri.parse(link))
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
            videoView.setOnErrorListener { mediaPlayer, i, i2 ->
                videoFragment.showSnackbarLong(R.string.error_loading)
                true
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.videoView.stopPlayback()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val ARG_NAME = "ARG_NAME"
        private const val ARG_LINK = "ARG_LINK"
        private const val TIMEOUT_SHOW_MS = 60000
        private const val PREVIEW_VIDEO_MS = 1

        fun newInstance(name: String, link: String) =
            VideoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putString(ARG_LINK, link)
                }
            }
    }
}
