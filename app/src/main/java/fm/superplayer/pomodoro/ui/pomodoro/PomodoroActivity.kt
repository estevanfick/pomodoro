package fm.superplayer.pomodoro.ui.pomodoro

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import fm.superplayer.pomodoro.PomodoroApp
import fm.superplayer.pomodoro.R
import fm.superplayer.pomodoro.model.PomodoroHistory
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat

class PomodoroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val pagerAdapter = PomodoroPagerAdapter(supportFragmentManager)
        container.adapter = pagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        data()
    }

    fun goToHistory(){
        container.currentItem = 1
    }

    fun data(){
        val history = listOf(PomodoroHistory("25:00", timeStringtoMilis("2017-08-27 14:00:00"), 1)
        , PomodoroHistory("25:00", timeStringtoMilis("2017-08-27 13:25:00"), 1)
        , PomodoroHistory("12:38", timeStringtoMilis("2017-08-27 12:00:00"), 0)
        , PomodoroHistory("25:00", timeStringtoMilis("2017-08-27 08:00:00"), 1)
        , PomodoroHistory("25:00", timeStringtoMilis("2017-08-26 14:00:00"), 1)
        , PomodoroHistory("25:00", timeStringtoMilis("2017-08-26 13:25:00"), 1)
        , PomodoroHistory("12:38", timeStringtoMilis("2017-08-26 12:00:00"), 0)
        , PomodoroHistory("25:00", timeStringtoMilis("2017-08-26 08:00:00"), 1))
        Single.fromCallable {
            PomodoroApp.database?.historyDao()?.deleteAll()
            history.forEach{
            PomodoroApp.database?.historyDao()?.insert(it)} }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    private fun timeStringtoMilis(time: String): Long {
        var milis: Long = 0

        try {
            val sd = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = sd.parse(time)
            milis = date.time
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return milis
    }
}
