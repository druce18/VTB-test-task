package by.vtb.test.presentation.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.vtb.test.domain.model.VideoLinks
import by.vtb.test.presentation.video.VideoFragment

class VideoLinksPagerAdapter(
    private val videoLinks: VideoLinks,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        val nameAndLinkFromPos = getNameAndLinkFromPos(position)
        return VideoFragment.newInstance(nameAndLinkFromPos.second)
    }

    /**
     * @return Pair (name, link)
     */
    fun getNameAndLinkFromPos(position: Int): Pair<String, String> {
        return when (position) {
            0 -> Pair(SINGLE_NAME_TAB, videoLinks.single)
            1 -> Pair(SPLIT_V_NAME_TAB, videoLinks.splitV)
            2 -> Pair(SPLIT_H_NAME_TAB, videoLinks.splitH)
            else -> {
                Pair(SRC_NAME_TAB, videoLinks.src)
            }
        }
    }

    companion object {

        private const val ITEM_COUNT = 4
        private const val SINGLE_NAME_TAB = "single"
        private const val SPLIT_V_NAME_TAB = "splitV"
        private const val SPLIT_H_NAME_TAB = "splitH"
        private const val SRC_NAME_TAB = "src"
    }
}
