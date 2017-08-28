package fm.superplayer.pomodoro.ui.pomodoro

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import fm.superplayer.pomodoro.R
import kotlinx.android.synthetic.main.activity_main.*

class PomodoroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val pagerAdapter = PomodoroPagerAdapter(supportFragmentManager)
        container.adapter = pagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

    }

    fun goToHistory(){
        container.currentItem = 1
    }
}
