package fm.superplayer.pomodoro.ui.pomodoro

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import fm.superplayer.pomodoro.ui.pomodoro.create.PomodoroCreateFragment
import fm.superplayer.pomodoro.ui.pomodoro.history.PomodoroHistoryFragment

class PomodoroPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> PomodoroCreateFragment.newInstance()
            else -> PomodoroHistoryFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 2
    }

}