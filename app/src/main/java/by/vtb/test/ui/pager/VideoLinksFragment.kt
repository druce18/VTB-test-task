package by.vtb.test.ui.pager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.vtb.test.R
import by.vtb.test.databinding.FragmentVideoLinksBinding
import by.vtb.test.extention.appComponent
import by.vtb.test.extention.setGone
import by.vtb.test.extention.setVisible
import by.vtb.test.repository.model.VideoLinks
import by.vtb.test.ui.UiState
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class VideoLinksFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: VideoLinksViewModel by viewModels { viewModelFactory }

    private var videoLinksAdapter: VideoLinksPagerAdapter? = null
    private var _binding: FragmentVideoLinksBinding? = null
    private val binding: FragmentVideoLinksBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideoLinksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {
            viewModel.uiState.collect { uiState ->
                showData(uiState)
            }
        }
    }

    private fun showData(uiState: UiState<VideoLinks>) {
        when (uiState) {
            is UiState.Success -> {
                showPager(uiState)
            }
            is UiState.Loading -> {
                binding.progressBar.setVisible()
            }
            is UiState.Error -> {
                showError(uiState)
            }
        }
    }

    private fun showPager(uiState: UiState.Success<VideoLinks>) {
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
        binding.progressBar.setGone()
        val snackbar = Snackbar.make(binding.videoLinksFragment, uiState.message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction(R.string.refresh) {
            viewModel.loadVideoLinks()
        }
        snackbar.show()
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

        @JvmStatic
        fun newInstance() = VideoLinksFragment()
    }
}
