package by.vtb.test.ui.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.vtb.test.repository.model.VideoLinks
import by.vtb.test.ui.video.VideoFragment

class VideoLinksPagerAdapter(
    private val videoLinks: VideoLinks,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val nameAndLinkFromPos = getNameAndLinkFromPos(position)
        return VideoFragment.newInstance(
            name = nameAndLinkFromPos.first,
            link = nameAndLinkFromPos.second
        )
    }

    fun getNameAndLinkFromPos(position: Int): Pair<String, String> {
        return when (position) {
            0 -> Pair("single", videoLinks.single)
            1 -> Pair("splitV", videoLinks.splitV)
            2 -> Pair("splitH", videoLinks.splitH)
            else -> {
                Pair("src", videoLinks.src)
            }
        }
    }
}
