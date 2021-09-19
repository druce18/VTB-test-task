package by.vtb.test.ui.pager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.vtb.test.R
import by.vtb.test.databinding.FragmentVideoLinksBinding
import by.vtb.test.extention.appComponent
import by.vtb.test.extention.setGone
import by.vtb.test.extention.setVisible
import by.vtb.test.extention.showSnackbarErrorIndefinite
import by.vtb.test.repository.model.VideoLinks
import by.vtb.test.ui.base.BaseFragment
import by.vtb.test.ui.base.UiState
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect

class VideoLinksFragment : BaseFragment() {

    private val viewModel: VideoLinksViewModel by viewModels()
    private var videoLinksAdapter: VideoLinksPagerAdapter? = null
    private var _binding: FragmentVideoLinksBinding? = null
    private val binding: FragmentVideoLinksBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoLinksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                showState(uiState)
            }
        }
    }

    private fun showState(uiState: UiState<VideoLinks>) {
        when (uiState) {
            is UiState.Success -> {
                showPagerWithVideo(uiState)
            }
            is UiState.Loading -> {
                binding.progressBar.setVisible()
            }
            is UiState.Error -> {
                showError(uiState)
            }
        }
    }

    private fun showPagerWithVideo(uiState: UiState.Success<VideoLinks>) {
        videoLinksAdapter = VideoLinksPagerAdapter(uiState.data, this)
        with(binding) {
            progressBar.setGone()
            viewPager.adapter = videoLinksAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = videoLinksAdapter!!.getNameAndLinkFromPos(position).first
            }.attach()
        }
    }

    private fun showError(uiState: UiState.Error) {
        with(binding) {
            progressBar.setGone()
            videoLinksFragment.showSnackbarErrorIndefinite(uiState.message, R.string.refresh) {
                viewModel.loadVideoLinks()
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
        videoLinksAdapter = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = VideoLinksFragment()
    }
}
